package me.gavin.gavhack.module.setting

import me.gavin.gavhack.misc.MathUtils

class FloatSetting(
    name: String,
    val defaultValue: Float,
    val minValue: Float,
    val maxValue: Float,
    val decimalPlaces: Int = 1
): ModSetting(name)  {

    /* Variable stays private so there is no chance of it accidentally going out of specified bounds */
    private var value = this.defaultValue

    /* We always clamp our value when setting it to be safe */
    fun setValue(valueIn: Float) {
        value = MathUtils.clampf(valueIn, this.minValue, this.maxValue)
    }

    fun getValue() : Float {
        return this.value
    }

    override fun reset() {
        setValue(this.defaultValue)
    }
}