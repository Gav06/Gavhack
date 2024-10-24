package me.gavin.gavhack.misc

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
}