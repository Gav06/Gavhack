package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.ToggleSetting

object NoRender: Module("NoRender", "Prevent rendering certain annoyances", ModCategory.RENDER) {

    val fog = addSetting(ToggleSetting("Fire", true))
    val portal = addSetting(ToggleSetting("Portal", true))
    val nausea = addSetting(ToggleSetting("Nausea", true))
    val water = addSetting(ToggleSetting("WaterOverlay", true))
}