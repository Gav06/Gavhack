package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.misc.BPos
import me.gavin.gavhack.misc.BlockUtil
import me.gavin.gavhack.misc.ProjectionHelper
import me.gavin.gavhack.misc.RenderUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.FloatSetting
import me.gavin.gavhack.module.setting.IntSetting
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.entity.Entity
import net.minecraft.item.ItemBlock
import net.minecraft.util.Vec3
import optifine.BlockPos
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object AutoBuild: Module("AutoBuild", "Builds stuff automatically", ModCategory.WORLD) {

    private val switch = addSetting(ToggleSetting("Switch", true))

    private val blocksPerTick = addSetting(IntSetting("BlocksPerTick", 1, 1, 10))

    private val blockList = ArrayList<BlockPos>()

    val arr = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, -1),
        intArrayOf(1, 0, -1),
        intArrayOf(1, 0, 0),
        intArrayOf(1, 0, 1),
        intArrayOf(0, 0, 1),
        intArrayOf(-1, 0, 1),
        intArrayOf(-1, 0, 0),
        intArrayOf(-1, 0, -1),
        intArrayOf(-1, 0, -2),
        intArrayOf(0, 0, -2),
        intArrayOf(1, 0, -2),
        intArrayOf(2, 0, -2),
        intArrayOf(2, 0, -1),
        intArrayOf(2, 0, 0),
        intArrayOf(2, 0, 1),
        intArrayOf(2, 0, 2),
        intArrayOf(1, 0, 2),
        intArrayOf(0, 0, 2),
        intArrayOf(-1, 0, 2),
        intArrayOf(-2, 0, 2),
        intArrayOf(-2, 0, 1),
        intArrayOf(-2, 0, 0),
        intArrayOf(-2, 0, -1),
        intArrayOf(-2, 0, -2),
    )

    private fun getStartingPosition(entity: Entity): BPos {
        return BPos(
            entity.posX.toInt() + if (entity.posX < 0.0) -1 else 0,
            entity.boundingBox.minY.toInt() - 1,
            entity.posZ.toInt() + if (entity.posZ < 0.0) -1 else 0
        )
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

    // function called every game tick
    private val tickListener = Listener<PlayerLivingTickEvent> {
        // build up our blockPos list

        if (!findBuildingBlock())
            return@Listener

        val start = getStartingPosition(mc.thePlayer)

        var blocksPlaced = 0
        for (coord in arr) {
            val pos = BPos(coord[0] + start.x, coord[1] + start.y, coord[2] + start.z)

            val block = mc.theWorld.getBlock(pos.x, pos.y, pos.z)

            if (!block.material.isReplaceable)
                continue

            if (BlockUtil.entityBlockDistance(pos, mc.thePlayer) > mc.playerController.blockReachDistance)
                continue

            val placement = BlockUtil.findValidBlockPlace(pos, mc.theWorld) ?: continue
            val placePos = placement.pos

            if (blocksPlaced >= blocksPerTick.getValue())
                break

            mc.playerController.onPlayerRightClick(
                mc.thePlayer, mc.theWorld, mc.thePlayer.currentEquippedItem,
                placePos.x, placePos.y, placePos.z, placement.side, Vec3.createVectorHelper(0.0, 0.0, 0.0)
            )
            mc.thePlayer.swingItem()
            blocksPlaced++
        }
    }

    private val renderListener = Listener<RenderWorldLastEvent> { (it as RenderWorldLastEvent)
        RenderUtils.prepare3d()

        for (pos in blockList) {
            RenderGlobal.drawOutlinedBoundingBox(RenderUtils.getBoxFromBlockPos(pos), 0xff55ff)
        }

        RenderUtils.release3d()
    }

    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)
        addEventListener(renderListener, RenderWorldLastEvent::class)



//        val p = BPos(0, 0, 0)
//        blockList.add(p)
//        blockList.add(p.north())
//        blockList.add(p.north().east())
//        blockList.add(p.east())
//        blockList.add(p.south().east())
//        blockList.add(p.south())
//        blockList.add(p.south().west())
//        blockList.add(p.west())
//        blockList.add(p.west().north())
//
//        blockList.add(p.west().north().north())
//        blockList.add(p.north().north())
//        blockList.add(p.north().north().east())
//        blockList.add(p.east().east().north().north())
//        blockList.add(p.east().east().north())
//        blockList.add(p.east().east())
//        blockList.add(p.east().east().south())
//        blockList.add(p.east().east().south().south())
//        blockList.add(p.south().south().east())
//        blockList.add(p.south().south())
//        blockList.add(p.south().south().west())
//        blockList.add(p.south().south().west().west())
//        blockList.add(p.west().west().south())
//        blockList.add(p.west().west())
//        blockList.add(p.west().west().north())
//        blockList.add(p.west().west().north().north())
//
//
//        val file = File("C:/Users/Gavin/Desktop/file.txt")
//        if (!file.exists())
//            file.createNewFile()
//
//        val buf = BufferedWriter(FileWriter(file))
//
//        val arr = intArrayOf()
//
//        buf.write("val arr = intArrayOf(")
//        for (pos in blockList) {
//            buf.write("intArrayOf(${pos.x}, ${pos.y}, ${pos.z}),\n")
//        }
//
//        buf.write(")")
//
//        buf.close()
    }
}