package me.gavin.gavhack.module.setting

import me.gavin.gavhack.utilities.MathUtils

class IntSetting(
    name: String,
    private val defaultValue: Int,
    val minValue: Int,
    val maxValue: Int,
): Setting(name)  {

    /* Variable stays private so there is no chance of it accidentally going out of specified bounds */
    private var value = this.defaultValue

    /* We always clamp our value when setting it to be safe */
    fun setValue(valueIn: Int) {
        value = MathUtils.clampi(valueIn, this.minValue, this.maxValue)
    }

    fun getValue() : Int {
        return this.value
    }

    override fun reset() {
        setValue(this.defaultValue)
    }
}