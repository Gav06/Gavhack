package me.gavin.gavhack.utilities

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import net.minecraft.util.Vec3
import me.gavin.gavhack.event.Listener
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.ActiveRenderInfo
import net.minecraft.client.renderer.GLAllocation
import net.minecraft.client.renderer.entity.RenderManager
import org.lwjgl.opengl.GL11
import org.lwjgl.util.glu.GLU

object ProjectionHelper {

    private val mc = Minecraft.getMinecraft()
    private var camPos: Vec3 = Vec3.createVectorHelper(0.0, 0.0, 0.0)

    private var modelView = GLAllocation.createDirectFloatBuffer(16)
    private var projection = GLAllocation.createDirectFloatBuffer(16)
    private var viewport = GLAllocation.createDirectFloatBuffer(16)

    fun worldToScreen(worldPos: Vec3, scaledRes: ScaledResolution): Vec3? {
        val pos = camPos.subtract(worldPos)
        val screenPos = GLAllocation.createDirectFloatBuffer(3)

        val result = GLU.gluProject(
                pos.xCoord.toFloat(),
                pos.yCoord.toFloat(),
                pos.zCoord.toFloat(),
                ActiveRenderInfo.modelview,
                ActiveRenderInfo.projection,
                ActiveRenderInfo.viewport,
                screenPos
        )

        val x = screenPos.get(0).toDouble() / scaledRes.scaleFactor
        val y = (mc.displayHeight - screenPos.get(1)).toDouble() / scaledRes.scaleFactor
        val z = screenPos.get(2).toDouble()

        return if (result && z < 1.0 && withinScreen(x, y, scaledRes.scaledWidth_double, scaledRes.scaledHeight_double))
            Vec3.createVectorHelper(x, y, z)
        else
            null
    }

    fun worldToScreen(worldPos: Vec3, width: Double, height: Double): Vec3? {
        val pos = camPos.subtract(worldPos)
        val screenPos = GLAllocation.createDirectFloatBuffer(3)

        val result = GLU.gluProject(
                pos.xCoord.toFloat(),
                pos.yCoord.toFloat(),
                pos.zCoord.toFloat(),
                ActiveRenderInfo.modelview,
                ActiveRenderInfo.projection,
                ActiveRenderInfo.viewport,
                screenPos
        )

        val x = screenPos.get(0).toDouble()
        val y = (mc.displayHeight - screenPos.get(1)).toDouble()
        val z = screenPos.get(2).toDouble()

        return if (result && withinScreen(x, y, width, height))
            Vec3.createVectorHelper(x, y, z)
        else
            null
    }

    private fun withinScreen(x: Double, y: Double, width: Double, height: Double): Boolean {
        return x in 0.0..width && y in 0.0..height
    }

    private fun updateMatrices() {
//        camPos = ActiveRenderInfo.projectViewFromEntity(mc.renderViewEntity, mc.timer.renderPartialTicks.toDouble())
        camPos = Vec3.createVectorHelper(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ)
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView)
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection)
        GL11.glGetFloat(GL11.GL_VIEWPORT, viewport)
    }

    private val renderWorld = Listener<RenderWorldLastEvent> {
        updateMatrices()
    }

    init {
        EventBus.addListener(renderWorld, RenderWorldLastEvent::class)
    }
}