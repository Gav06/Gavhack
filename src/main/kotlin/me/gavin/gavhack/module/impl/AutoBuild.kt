package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.misc.BlockUtil
import me.gavin.gavhack.misc.RenderUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.NumberSetting
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Vec3
import optifine.BlockPos
import java.util.concurrent.CopyOnWriteArrayList

object AutoBuild: Module("AutoBuild", "Builds stuff automatically", ModCategory.WORLD) {

    private val width = addSetting(NumberSetting("Width", 5f, 3f, 6f, decimalPlaces = 0))
    private val depth = addSetting(NumberSetting("Depth", 4f, 2f, 6f, decimalPlaces = 0))

    private val blockPositions = CopyOnWriteArrayList<BlockPos>()

    private val tickListener = Listener<PlayerLivingTickEvent> {
        // build up our blockPos list

        blockPositions.clear()

        val below = BlockPos(
            mc.thePlayer.posX.toInt() + if (mc.thePlayer.posX < 0.0) -1 else 0,
            mc.thePlayer.boundingBox.minY.toInt() - 1,
            mc.thePlayer.posZ.toInt() + if (mc.thePlayer.posZ < 0.0) -1 else 0
        )

        blockPositions.add(below)

        var prevForward: BlockPos = below
        for (i in 1..<depth.getValue().toInt()) {
            val newForward = BlockUtil.forward(prevForward, mc.thePlayer)
            // fill in left and right
            var sideLeft = newForward
            var sideRight = newForward
            repeat(width.getValue().toInt() - 2) {
                val newLeft = BlockUtil.left(sideLeft, mc.thePlayer)
                val newRight = BlockUtil.right(sideRight, mc.thePlayer)
                blockPositions.add(newLeft)
                blockPositions.add(newRight)
                sideLeft = newLeft
                sideRight= newRight
            }

            blockPositions.add(newForward)
            prevForward = newForward
        }

        var prevLeft: BlockPos = below
        for (i in 1..<width.getValue().toInt() - 1) {
            val newLeft = BlockUtil.left(prevLeft, mc.thePlayer)
            blockPositions.add(newLeft)
            prevLeft = newLeft
        }

        var prevRight: BlockPos = below
        for (i in 1..<width.getValue().toInt() - 1) {
            val newRight = BlockUtil.right(prevRight, mc.thePlayer)
            blockPositions.add(newRight)
            prevRight = newRight
        }

        /**/

        if (mc.thePlayer.currentEquippedItem == null
            || mc.thePlayer.currentEquippedItem.item !is ItemBlock
            || !Block.getBlockFromItem(mc.thePlayer.currentEquippedItem.item).isNormalCube) {
            // attempt to find block in hotbar
            var foundItem = false
            for (i in 0..8) {
                val stack = mc.thePlayer.inventory.getStackInSlot(i)

                if (stack != null && stack.item is ItemBlock && Block.getBlockFromItem(stack.item).isNormalCube) {
                    mc.thePlayer.inventory.currentItem = i
                    foundItem = true
                    break
                }
            }

            if (!foundItem)
                return@Listener
        }

        for (pos in blockPositions) {
            val block = mc.theWorld.getBlock(pos.x, pos.y, pos.z)

            if (!block.material.isReplaceable)
                continue

            val placement = BlockUtil.findValidBlockPlace(pos, mc.theWorld)

            if (placement != null) {
                val placePos = placement.first
                mc.playerController.onPlayerRightClick(
                    mc.thePlayer,
                    mc.theWorld,
                    mc.thePlayer.currentEquippedItem,
                    placePos.x, placePos.y, placePos.z, placement.second,
                    Vec3.createVectorHelper(0.0, 0.0, 0.0)
                )
                mc.thePlayer.swingItem()
                return@Listener
            }
        }
    }

    private val renderListener = Listener<RenderWorldLastEvent> { (it as RenderWorldLastEvent)

        RenderUtils.prepare3d()

        for (pos in blockPositions) {
            val x = pos.x.toDouble()
            val y = pos.y.toDouble()
            val z = pos.z.toDouble()

            val block = mc.theWorld.getBlock(pos.x, pos.y, pos.z)

            if (block.material.isReplaceable || block.material == Blocks.air.material) {

                RenderGlobal.drawOutlinedBoundingBox(
                    AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0)
                        .offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ),
                    0x00ff00
                )
            }


        }

        RenderUtils.release3d()
    }

    fun shouldPlaceBlockAtPos(pos: BlockPos): Boolean {
        return mc.theWorld.getBlock(pos.x, pos.y, pos.z).isCollidable
    }

    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)
        addEventListener(renderListener, RenderWorldLastEvent::class)
    }
}