package me.gavin.gavhack.misc

import net.minecraft.entity.Entity
import java.math.BigDecimal
import java.math.RoundingMode
import javax.vecmath.Vector3d

object MathUtils {

    fun normalizef(x: Float, min: Float, max: Float): Float {
        return (x - min) / (max - min)
    }

    fun normalized(x: Double, min: Double, max: Double): Double {
        return (x - min) / (max - min)

    }

    fun lerpf(then: Float, now: Float, delta: Float): Float {
        return then + (now - then) * delta
    }

    fun lerpd(then: Double, now: Double, delta: Double): Double {
        return then + (now - then) * delta
    }

    fun lerpEntity(e: Entity, d: Float): Vector3d {
        return Vector3d(
                lerpd(e.lastTickPosX, e.posX, d.toDouble()),
                lerpd(e.lastTickPosY, e.posY, d.toDouble()),
                lerpd(e.lastTickPosZ, e.posZ, d.toDouble())
        )
    }

    fun clampf(x: Float, min: Float, max: Float): Float {
        return min.coerceAtLeast(x.coerceAtMost(max))
    }

    fun clampd(x: Double, min: Double, max: Double): Double {
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