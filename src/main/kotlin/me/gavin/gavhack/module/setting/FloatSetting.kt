package me.gavin.gavhack.module.setting

import me.gavin.gavhack.gui.api.Visibility
import me.gavin.gavhack.utilities.MathUtils
import java.util.function.Predicate

class FloatSetting(
        name: String,
        private val defaultValue: Float,
        val minValue: Float,
        val maxValue: Float,
        val decimalPlaces: Int = 1,
        visibility: Visibility? = null
): Setting(name, visibility)  {

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