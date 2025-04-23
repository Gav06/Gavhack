package me.gavin.gavhack.module.setting

import me.gavin.gavhack.gui.api.Visibility

abstract class Setting(val name: String, val visibility: Visibility? = null) {

    abstract fun reset()
}