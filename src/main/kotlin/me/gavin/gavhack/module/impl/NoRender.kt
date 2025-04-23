package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.BooleanSetting

object NoRender: Module("NoRender", "Prevent rendering certain annoyances", ModCategory.RENDER) {

    val portal = addSetting(BooleanSetting("Portal", true))
    val nausea = addSetting(BooleanSetting("Nausea", true))
    val blindness = addSetting(BooleanSetting("Blindness", true))
    val water = addSetting(BooleanSetting("Water", true))
    val fire = addSetting(BooleanSetting("Fire", true))
    val liquidFog = addSetting(BooleanSetting("LiquidFog", true))
    val hurtCamera = addSetting(BooleanSetting("HurtCam", true))
}