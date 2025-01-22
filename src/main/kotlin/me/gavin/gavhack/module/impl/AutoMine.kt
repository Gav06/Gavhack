package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ClientTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.NumberSetting
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.util.MovingObjectPosition

object AutoMine: Module("AutoMine", "Mine automatically", ModCategory.WORLD) {

    private val noBreak = addSetting(ToggleSetting("NoBreak", true))

    private var shouldMine = false

    private val tickPre = Listener<ClientTickEvent> {
        if (mc.currentScreen == null && mc.theWorld != null) {
            val result = mc.objectMouseOver

            shouldMine = if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                if (toolCheck()) {
                    if (noBreak.value)
                        mc.thePlayer.currentEquippedItem.maxDamage - mc.thePlayer.currentEquippedItem.itemDamage >= 1
                    else
                        true
                } else {
                    true
                }
            } else {
                false
            }

            if (shouldMine) {
                mc.gameSettings.keyBindAttack.pressed = true
            }
        }
    }

    private fun toolCheck(): Boolean {
        return (mc.thePlayer.currentEquippedItem != null && mc.thePlayer.currentEquippedItem.isItemStackDamageable)
    }

    private val tickPost = Listener<ClientTickEvent.Post> {
        if (shouldMine && mc.gameSettings.keyBindAttack.pressed) {
            mc.gameSettings.keyBindAttack.pressed = false
        }
    }

    override fun onDisable() {
        mc.gameSettings.keyBindAttack.pressed = false
    }

    init {
        addEventListener(tickPre, ClientTickEvent::class)
        addEventListener(tickPost, ClientTickEvent.Post::class)
    }
}