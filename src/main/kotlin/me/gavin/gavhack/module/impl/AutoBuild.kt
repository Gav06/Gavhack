package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.utilities.BPos
import me.gavin.gavhack.utilities.BlockUtil
import me.gavin.gavhack.utilities.RenderUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.IntSetting
import me.gavin.gavhack.module.setting.BooleanSetting
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.entity.Entity
import net.minecraft.item.ItemBlock
import net.minecraft.network.play.client.C0BPacketEntityAction
import net.minecraft.util.Vec3
import optifine.BlockPos

object AutoBuild: Module("AutoBuild", "Builds stuff automatically", ModCategory.WORLD) {

    private val switch = addSetting(BooleanSetting("Switch", true))
    private val autoSneak = addSetting(BooleanSetting("AutoSneak", true))

    private val blocksPerTick = addSetting(IntSetting("BlocksPerTick", 1, 1, 10))

    private val blockList = ArrayList<BlockPos>()

    private val floorOffsets = arrayOf(
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
        intArrayOf(-2, 0, -2)
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

            if (stack.item !is ItemBlock || !Block.getBlockFromItem(stack.item).material.isSolid) continue

            mc.thePlayer.inventory.currentItem = i
            return true
        }

        return false
    }

    private fun isHoldingValidBlock(): Boolean {
        return mc.thePlayer.currentEquippedItem?.let {
            it.item is ItemBlock && Block.getBlockFromItem(it.item).material.isSolid
        } ?: false
    }

    private fun placeBlock(placement: BlockUtil.Placement) {
        val placePos = placement.pos
        val placeBlock = mc.theWorld.getBlock(placePos.x, placePos.y, placePos.z)
        val prevSneak = mc.thePlayer.movementInput.sneak
        val shouldSneak = autoSneak.value && !prevSneak && placeBlock.onBlockActivated(
                mc.theWorld,
                placePos.x, placePos.y, placePos.z,
                mc.thePlayer, placement.side,
                0f, 0f, 0f
        )


        if (shouldSneak) {
            mc.thePlayer.movementInput.sneak = true
            mc.thePlayer.sendQueue.addToSendQueue(C0BPacketEntityAction(mc.thePlayer, 1))
        }
        mc.playerController.onPlayerRightClick(
            mc.thePlayer, mc.theWorld, mc.thePlayer.currentEquippedItem,
            placePos.x, placePos.y, placePos.z, placement.side, Vec3.createVectorHelper(0.0, 0.0, 0.0)
        )
        mc.thePlayer.swingItem()

        if (shouldSneak) {
            mc.thePlayer.sendQueue.addToSendQueue(C0BPacketEntityAction(mc.thePlayer, 2))
            mc.thePlayer.movementInput.sneak = false
        }
    }

    // function called every game tick
    private val tickListener = Listener<PlayerLivingTickEvent> {
        // build up our blockPos list
        if (mc.currentScreen != null)
            return@Listener

        if (!findBuildingBlock())
            return@Listener

        val start = getStartingPosition(mc.thePlayer)

        var blocksPlaced = 0
        for (offset in floorOffsets) {
            if (blocksPlaced >= blocksPerTick.getValue())
                break

            val pos = BPos(offset[0] + start.x, offset[1] + start.y, offset[2] + start.z)

            val block = mc.theWorld.getBlock(pos.x, pos.y, pos.z)

            if (!block.material.isReplaceable)
                continue

            if (BlockUtil.entityBlockDistance(pos, mc.thePlayer) > mc.playerController.blockReachDistance)
                continue

            val placement = BlockUtil.findValidBlockPlace(pos, mc.theWorld) ?: continue

            placeBlock(placement)
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
    }
}