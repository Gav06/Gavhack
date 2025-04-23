package me.gavin.gavhack.module.setting

import me.gavin.gavhack.gui.api.Visibility

class BooleanSetting(
    name: String,
    private val defaultValue: Boolean,
    visibility: Visibility? = null
): Setting(name, visibility) {

    var value = defaultValue

    fun toggle() {
        value = !value
    }

    override fun reset() {
        this.value = defaultValue
    }

}