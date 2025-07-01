package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.FloatSetting

object ViewClip: Module("ViewClip", "Lets 3rd person camera wallclip", ModCategory.RENDER) {

    val distance = addSetting(FloatSetting("Distance", 4.0f, 1.0f, 10.0f))

}