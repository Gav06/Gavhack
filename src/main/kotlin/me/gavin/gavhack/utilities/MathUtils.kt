package me.gavin.gavhack.utilities

import net.minecraft.entity.Entity
import net.minecraft.util.Vec3
import org.lwjgl.util.vector.Vector3f
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.sqrt

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

    fun lerpEntity(e: Entity, d: Float): Vec3 {
        return Vec3.createVectorHelper(
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

    fun vec3ToIntColor(vec3: Vector3f): Int {
        return vec3ToIntColor(vec3.x, vec3.y, vec3.z)
    }

    fun distance3f(a: Vector3f, b: Vector3f): Float {
        return distance3f(a.x, a.y, a.z, b.x, b.y, b.z)
    }

    fun distance3f(ax: Float, ay: Float, az: Float, bx: Float, by: Float, bz: Float): Float {
        return sqrt(ax * bx + ay * by + az * bz)
    }

    fun distance3d(a: Vec3, b: Vec3): Double {
        return distance3d(a.xCoord, a.yCoord, a.zCoord, b.xCoord, b.yCoord, b.zCoord)
    }

    fun distance3d(ax: Double, ay: Double, az: Double, bx: Double, by: Double, bz: Double): Double {
        return sqrt((bx-ax) * (bx-ax) + (by-ay)*(by-ay) + (bz-az)*(bz-az))
    }

    fun vec3ToIntColor(r: Float, g: Float, b: Float): Int {
        // Clamp the float values to the range [0.0, 1.0] to ensure valid color components.
        val clampedR = r.coerceIn(0.0f, 1.0f)
        val clampedG = g.coerceIn(0.0f, 1.0f)
        val clampedB = b.coerceIn(0.0f, 1.0f)

        // Convert float components (0.0-1.0) to integer components (0-255).
        // Multiply by 255 and cast to Int.
        val intR = (clampedR * 255).toInt()
        val intG = (clampedG * 255).toInt()
        val intB = (clampedB * 255).toInt()

        // Alpha is always 255 (0xFF).
        val intA = 255

        // Combine the RGBA components into a single integer in 0xAARRGGBB format.
        // Use bitwise left shifts (shl) to position each component correctly.
        // The 'or' operator combines the shifted values.
        return (intA shl 24) or (intR shl 16) or (intG shl 8) or intB
    }
}