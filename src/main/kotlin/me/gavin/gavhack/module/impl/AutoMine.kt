package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ClientTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.util.MovingObjectPosition

object AutoMine: Module("AutoMine", "Mine automatically", ModCategory.WORLD) {

    private var shouldMine = false

    private val tickPre = Listener<ClientTickEvent> {
        if (mc.currentScreen == null && mc.theWorld != null) {
            val result = mc.objectMouseOver
            shouldMine = result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK

            if (shouldMine) {
                mc.gameSettings.keyBindAttack.pressed = true
            }
        }
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