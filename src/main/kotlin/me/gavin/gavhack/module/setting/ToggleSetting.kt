package me.gavin.gavhack.module.setting

class ToggleSetting(name: String, private val defaultValue: Boolean): ModSetting(name) {

    var value = defaultValue

    fun toggle() {
        value = !value
    }

    override fun reset() {
        this.value = defaultValue
    }

}