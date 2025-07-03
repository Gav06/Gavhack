package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.BooleanSetting
import me.gavin.gavhack.module.setting.FloatSetting

object TimerMod: Module("Timer", "Change game speed", ModCategory.MISC) {

    val ticksPerSecond = addSetting(FloatSetting("Speed", 1.0f, 0.01f, 5.0f, 2))
    val inGameOnly = addSetting(BooleanSetting("InGameOnly", true))
}