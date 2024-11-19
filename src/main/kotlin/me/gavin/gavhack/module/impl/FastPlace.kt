package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module

object FastPlace: Module("FastPlace", "Removes use cooldown", ModCategory.WORLD) {

    private val tickListener = Listener<PlayerLivingTickEvent> {
        mc.rightClickDelayTimer = 0
    }

    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)
    }
}