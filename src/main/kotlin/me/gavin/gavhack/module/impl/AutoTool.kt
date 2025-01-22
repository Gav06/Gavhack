package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.block.Block
import net.minecraft.util.MovingObjectPosition

object AutoTool: Module("AutoTool", "Selects tool automatically based on what you're mining", ModCategory.WORLD) {

    private val noBreak = addSetting(ToggleSetting("NoBreak", true))

    private val playerTickListener = Listener<PlayerLivingTickEvent> {
        if (mc.gameSettings.keyBindAttack.isKeyPressed) {
            val result = mc.objectMouseOver

            if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {


                val block = mc.theWorld.getBlock(result.blockX, result.blockY, result.blockZ)

                val slot = findBestSlot(block)

                if (slot != -1) {
                    if (slot != mc.thePlayer.inventory.currentItem) {
                        mc.thePlayer.inventory.currentItem = slot
                    }
                }
            }
        }
    }

    private fun findBestSlot(block: Block): Int {
        var bestSlot = 0
        var bestSpeed = 1f
        var mostDamage = 0

        for (i in (0..8)) {
            val item = mc.thePlayer.inventory.getStackInSlot(i) ?: continue

            if (item.getDiggingSpeed(block) > bestSpeed) {
                bestSpeed = item.getDiggingSpeed(block)
                bestSlot = i
            } else if (item.getDiggingSpeed(block) == bestSpeed) {
                if (item.itemDamage > mostDamage) {
                    mostDamage = item.itemDamage
                    bestSlot = i
                }
            }
        }

        // Negative 1 if there is no tool for the job
        if (bestSpeed == 1f)
            return -1

        return bestSlot
    }

//    private fun getDigSpeed(stack: ItemStack, pos: BlockPos, block: Block): Double {
//        val speed = stack.getMiningSpeedMultiplier()
//    }

    init {
        addEventListener(playerTickListener, PlayerLivingTickEvent::class)
    }
}