package me.gavin.gavhack.module.setting

import me.gavin.gavhack.gui.api.Visibility
import java.awt.Color

class ColorSetting(name: String, val defaultColor: Color, visibility: Visibility? = null): Setting(name, visibility) {

    var currentColor = defaultColor

    override fun reset() {
        currentColor = defaultColor
    }
}