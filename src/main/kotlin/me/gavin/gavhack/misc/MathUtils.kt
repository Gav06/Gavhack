package me.gavin.gavhack.misc

import java.math.BigDecimal
import java.math.RoundingMode

object MathUtils {

    fun normalizef(x: Float, min: Float, max: Float) : Float {
        return (x - min) / (max - min)
    }

    fun normalized(x: Double, min: Double, max: Double) : Double {
        return (x - min) / (max - min)

    }

    fun lerpf(then: Float, now: Float, delta: Float) : Float {
        return (then * (1.0f - delta)) + (now * then)
    }

    fun lerpd(then: Double, now: Double, delta: Double) : Double {
        return (then * (1.0f - delta)) + (now * then)
    }

    fun clampf(x: Float, min: Float, max: Float) : Float {
        return min.coerceAtLeast(x.coerceAtMost(max))
    }

    fun clampd(x: Double, min: Double, max: Double) : Double {
        return min.coerceAtLeast(x.coerceAtMost(max))
    }

    fun clampi(x: Int, min: Int, max: Int) : Int {
        return min.coerceAtLeast(x.coerceAtMost(max))
    }

    fun roundToPlacef(value: Float, places: Int) : Float {
        return roundToPlaced(value.toDouble(), places).toFloat()
    }

    fun roundToPlaced(value: Double, places: Int) : Double {
        if (places < 0) {
            throw IllegalStateException("Cannot have negative amount of decimal places!")
        }

        return  BigDecimal(value).setScale(places, RoundingMode.HALF_UP).toDouble()
    }
}