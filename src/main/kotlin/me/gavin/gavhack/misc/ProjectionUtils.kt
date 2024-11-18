package me.gavin.gavhack.misc

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GLAllocation
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector4f

object ProjectionUtils {
    private val mc = Minecraft.getMinecraft()
    private val modelMatrix = Matrix4f()
    private val projectionMatrix = Matrix4f()
    private var resolution = ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight)
    var camPos = Vec3.createVectorHelper(0.0, 0.0, 0.0)

    fun updateMatrix() {
        if (mc.renderViewEntity == null) return

        val viewerPos = mc.renderViewEntity
        val partialTicks = mc.timer.renderPartialTicks

        // Get interpolated player position
        val viewerX = viewerPos.lastTickPosX + (viewerPos.posX - viewerPos.lastTickPosX) * partialTicks
        val viewerY = viewerPos.lastTickPosY + (viewerPos.posY - viewerPos.lastTickPosY) * partialTicks
        val viewerZ = viewerPos.lastTickPosZ + (viewerPos.posZ - viewerPos.lastTickPosZ) * partialTicks

        // Store the camera position
        camPos = Vec3.createVectorHelper(viewerX, viewerY, viewerZ)

        // Save current matrix state
        GL11.glPushMatrix()

        // Setup camera transform
        GL11.glMatrixMode(GL11.GL_MODELVIEW)
        GL11.glLoadIdentity()

        // Apply camera rotation with proper order and angles
        val pitch = viewerPos.prevRotationPitch + (viewerPos.rotationPitch - viewerPos.prevRotationPitch) * partialTicks
        val yaw = viewerPos.prevRotationYaw + (viewerPos.rotationYaw - viewerPos.prevRotationYaw) * partialTicks

        // Minecraft-specific camera transformations
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f)
        GL11.glRotatef(yaw + 180.0f, 0.0f, 1.0f, 0.0f)
        GL11.glTranslatef(-viewerX.toFloat(), -viewerY.toFloat(), -viewerZ.toFloat())

        // Get matrices
        loadMatrix(modelMatrix, GL11.GL_MODELVIEW_MATRIX)
        loadMatrix(projectionMatrix, GL11.GL_PROJECTION_MATRIX)

        // Restore matrix state
        GL11.glPopMatrix()

        resolution = ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight)
    }

    private fun loadMatrix(matrix: Matrix4f, glBit: Int) {
        val floatBuffer = GLAllocation.createDirectFloatBuffer(16)
        GL11.glGetFloat(glBit, floatBuffer)
        matrix.load(floatBuffer)
    }

    fun toScaledScreenPos(posIn: Vec3): Vec3 {
        val screenPos = worldToScreen(posIn, true)
        return if (screenPos != null) {
            Vec3.createVectorHelper(screenPos.first, screenPos.second, 0.0)
        } else {
            Vec3.createVectorHelper(0.0, 0.0, -1.0)
        }
    }

    fun toScreenPos(posIn: Vec3): Vec3 {
        val screenPos = worldToScreen(posIn, false)
        return if (screenPos != null) {
            Vec3.createVectorHelper(screenPos.first, screenPos.second, 0.0)
        } else {
            Vec3.createVectorHelper(0.0, 0.0, -1.0)
        }
    }

    private fun worldToScreen(posIn: Vec3, scaled: Boolean): Pair<Double, Double>? {
        val vector4f = Vector4f(
                (posIn.xCoord - camPos.xCoord).toFloat(),
                (posIn.yCoord - camPos.yCoord).toFloat(),
                (posIn.zCoord - camPos.zCoord).toFloat(),
                1f
        )

        // Transform position
        transform(vector4f, modelMatrix)
        transform(vector4f, projectionMatrix)

        // Perspective divide check
        if (vector4f.w <= 0.0f) return null

        // Perform perspective divide
        vector4f.x /= vector4f.w
        vector4f.y /= vector4f.w
        vector4f.z /= vector4f.w

        val width = if (scaled) resolution.scaledWidth else mc.displayWidth
        val height = if (scaled) resolution.scaledHeight else mc.displayHeight

        val x = (width / 2.0) * (vector4f.x + 1.0)
        val y = (height / 2.0) * (1.0 - vector4f.y)

        // Check if point is visible
        return if (isVisible(x, y, width, height) && vector4f.z < 1) {
            Pair(x, y)
        } else null
    }

    private fun transform(vec: Vector4f, matrix: Matrix4f) {
        val x = vec.x
        val y = vec.y
        val z = vec.z
        val w = vec.w
        vec.x = x * matrix.m00 + y * matrix.m10 + z * matrix.m20 + w * matrix.m30
        vec.y = x * matrix.m01 + y * matrix.m11 + z * matrix.m21 + w * matrix.m31
        vec.z = x * matrix.m02 + y * matrix.m12 + z * matrix.m22 + w * matrix.m32
        vec.w = x * matrix.m03 + y * matrix.m13 + z * matrix.m23 + w * matrix.m33
    }

    private fun isVisible(x: Double, y: Double, width: Int, height: Int): Boolean {
        return x >= -1 && x <= width + 1 && y >= -1 && y <= height + 1
    }

    fun debug(posIn: Vec3): String {
        val screenPos = worldToScreen(posIn, true)
        return buildString {
            append("World Pos: (${posIn.xCoord}, ${posIn.yCoord}, ${posIn.zCoord})\n")
            append("Camera Pos: (${camPos.xCoord}, ${camPos.yCoord}, ${camPos.zCoord})\n")
            if (screenPos != null) {
                append("Screen Pos: (${screenPos.first}, ${screenPos.second})\n")
                append("Is Visible: ${isVisible(screenPos.first, screenPos.second, resolution.scaledWidth, resolution.scaledHeight)}")
            } else {
                append("Failed to project point")
            }
        }
    }
}