package me.gavin.gavhack.gui.click

import me.gavin.gavhack.gui.api.GuiComponent
import me.gavin.gavhack.module.setting.Setting

abstract class SettingComponent(open val setting: Setting, x: Int, y: Int, w: Int, h: Int): GuiComponent(x, y, w, h) {

    fun isVisible(): Boolean {
        return if (setting.visibility != null) {
            setting.visibility!!.test()
        } else {
            true
        }
    }
}