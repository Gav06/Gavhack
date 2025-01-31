package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.misc.BlockUtil
import me.gavin.gavhack.misc.RenderUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.NumberSetting
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.block.Block
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.entity.Entity
import net.minecraft.item.ItemBlock
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Vec3
import optifine.BlockPos

object AutoBuild: Module("AutoBuild", "Builds stuff automatically", ModCategory.WORLD) {

    private val switch = addSetting(ToggleSetting("Switch", true))

    private val horizonalRadius = addSetting(NumberSetting("H. Radius", 2f, 1f, 4f, decimalPlaces = 0))
    private val depth = addSetting(NumberSetting("Depth", 4f, 2f, 6f, decimalPlaces = 0))

    private val blockPositions = ArrayList<BlockPos>()
    private val placeQueue = ArrayList<BlockUtil.Placement>()

    private fun getStartingPosition(entity: Entity): BlockPos {
        return BlockPos(
            entity.posX.toInt() + if (entity.posX < 0.0) -1 else 0,
            entity.boundingBox.minY.toInt() - 1,
            entity.posZ.toInt() + if (entity.posZ < 0.0) -1 else 0
        )
    }

    private fun genHorizontalPositions(startPos: BlockPos, radius: Int) {
        var leftPos = startPos
        var rightPos = startPos
        repeat(radius) {
            leftPos = BlockUtil.left(leftPos, mc.thePlayer)
            rightPos = BlockUtil.right(rightPos, mc.thePlayer)
            blockPositions.add(leftPos)
            blockPositions.add(rightPos)
        }
    }

    private fun genDepthPositions(startPos: BlockPos, depth: Int, radius: Int) {
        var forwardPos = startPos
        repeat(depth - 1) {
            forwardPos = BlockUtil.forward(forwardPos, mc.thePlayer)
            blockPositions.add(forwardPos)

            var leftPos = forwardPos
            var rightPos = forwardPos
            repeat(radius) {
                leftPos = BlockUtil.left(leftPos, mc.thePlayer)
                rightPos = BlockUtil.right(rightPos, mc.thePlayer)
                blockPositions.add(leftPos)
                blockPositions.add(rightPos)
            }
        }
    }
    private fun genBlockPositions() {
        blockPositions.clear()

        val start = getStartingPosition(mc.thePlayer)

        blockPositions.add(start)

        val radius = horizonalRadius.getValue().toInt()
        genHorizontalPositions(start, radius)

        val depthVal = depth.getValue().toInt()
        genDepthPositions(start, depthVal, radius)
    }


    private fun findBuildingBlock(): Boolean {
        if (isHoldingValidBlock()) return true
        if (!switch.value) return false

        return switchToValidBlock()
    }

    private fun switchToValidBlock(): Boolean {
        // attempt to find block in hot bar
        for (i in 0..8) {
            val stack = mc.thePlayer.inventory.getStackInSlot(i) ?: continue

            if (stack.item !is ItemBlock || !Block.getBlockFromItem(stack.item).isNormalCube) continue

            mc.thePlayer.inventory.currentItem = i
            return true
        }

        return false
    }


    private fun isHoldingValidBlock(): Boolean {
        return mc.thePlayer.currentEquippedItem?.let {
            it.item is ItemBlock && Block.getBlockFromItem(it.item).isNormalCube
        } ?: false
    }

    private fun buildPlaceQueue() {
        for (pos in blockPositions) {
            val block = mc.theWorld.getBlock(pos.x, pos.y, pos.z)

            if (!block.material.isReplaceable) continue

            val placement = BlockUtil.findValidBlockPlace(pos, mc.theWorld) ?: continue

            if (!placeQueue.contains(placement))
                placeQueue.add(placement)
        }
    }

    private fun attemptBlockPlacement() {
        while (placeQueue.isNotEmpty()) {
            if (placeQueue.isEmpty())
                break

            val placement = placeQueue.first()
            placeQueue.removeFirst()
            val pos = placement.pos

            if (pos in blockPositions)
                continue

            if (BlockUtil.entityBlockDistance(pos, mc.thePlayer) > mc.playerController.blockReachDistance) continue

            mc.playerController.onPlayerRightClick(
                mc.thePlayer, mc.theWorld, mc.thePlayer.currentEquippedItem,
                pos.x, pos.y, pos.z, placement.side, Vec3.createVectorHelper(0.0, 0.0, 0.0)
            )
            mc.thePlayer.swingItem()
            break
        }
    }


    private val tickListener = Listener<PlayerLivingTickEvent> {
        // build up our blockPos list
        genBlockPositions()

        if (!findBuildingBlock()) {
            placeQueue.clear()
            return@Listener
        }

        buildPlaceQueue()
        attemptBlockPlacement()
    }

    private val renderListener = Listener<RenderWorldLastEvent> { (it as RenderWorldLastEvent)
        RenderUtils.prepare3d()

        for (placement in placeQueue) {
            val pos = placement.pos
            RenderUtils.renderBlockSideOffset(pos.x, pos.y, pos.z, placement.side, 0x8055ff55.toInt())
        }

        RenderUtils.release3d()
    }

    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)
        addEventListener(renderListener, RenderWorldLastEvent::class)
    }
}