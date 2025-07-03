package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.FloatSetting

object IceSpeed: Module("IceSpeed", "Move quicker on ice", ModCategory.MOVEMENT) {

    val speed = addSetting(FloatSetting("Speed", 0.68f, 0.1f, 1.0f, 2))

}