package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.util.MovingObjectPosition

object AutoTool: Module("AutoTool", "Selects tool automatically based on what you're mining", ModCategory.WORLD) {

    private val playerTickListener = Listener<PlayerLivingTickEvent> {
        if (mc.gameSettings.keyBindAttack.isPressed) {
            val result = mc.objectMouseOver

            if (result.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
                return@Listener


        }
    }

    init {
        addEventListener(playerTickListener, PlayerLivingTickEvent::class)
    }
}