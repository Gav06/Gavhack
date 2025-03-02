package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.CancelSprintEvent
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.ToggleSetting

object Sprint: Module("Sprint", "Automatically sprints", ModCategory.MOVEMENT) {

    val mode = addSetting(EnumSetting("Mode", Mode.LEGIT))
    val collideCheck = addSetting(ToggleSetting("CollideCheck", true))

    private val playerTickListener = Listener<PlayerLivingTickEvent> {
        if (collideCheck.value && mc.thePlayer.isCollidedHorizontally)
            return@Listener

        if (mode.enumValue() == Mode.LEGIT) {
            if (mc.thePlayer.moveForward > 0.0 && !mc.thePlayer.isCollidedHorizontally) {
                mc.thePlayer.isSprinting = true
            }
        } else {
            if (mc.thePlayer.moveForward != 0f || mc.thePlayer.moveStrafing != 0f) {
                mc.thePlayer.isSprinting = true
            } else {
                if (mc.thePlayer.isSprinting)
                    mc.thePlayer.isSprinting = false
            }
        }
    }

    private val cancelSprintListener = Listener<CancelSprintEvent> {
        if (mode.enumValue() == Mode.RAGE)
            it.cancelled = true

    }

    init {
        addEventListener(playerTickListener, PlayerLivingTickEvent::class)
        addEventListener(cancelSprintListener, CancelSprintEvent::class)
    }

    enum class Mode {
        LEGIT,
        RAGE
    }
}