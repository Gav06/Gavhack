package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.ToggleSetting

object NoRender: Module("NoRender", "Prevent rendering certain annoyances", ModCategory.RENDER) {

    val portal = addSetting(ToggleSetting("Portal", true))
    val nausea = addSetting(ToggleSetting("Nausea", true))
    val blindness = addSetting(ToggleSetting("Blindness", true))
    val water = addSetting(ToggleSetting("Water", true))
    val fire = addSetting(ToggleSetting("Fire", true))
    val liquidFog = addSetting(ToggleSetting("LiquidFog", true))
}