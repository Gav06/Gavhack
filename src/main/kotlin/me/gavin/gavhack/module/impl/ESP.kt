package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.renderer.ActiveRenderInfo
import net.minecraft.client.renderer.GLAllocation
import net.minecraft.util.Vec3
import org.lwjgl.util.glu.GLU


object ESP : Module("ESP", "See entities through walls", ModCategory.RENDER) {

    private val renderOverlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (!isEnabled())
            return@Listener



        val worldPos = Vec3.createVectorHelper(0.0, 6.0, 0.0)
        val camPos = ActiveRenderInfo.projectViewFromEntity(mc.renderViewEntity, mc.timer.renderPartialTicks.toDouble())
        val realPos = camPos.subtract(worldPos)

        val screenPos = GLAllocation.createDirectFloatBuffer(3)

        val result = GLU.gluProject(
            realPos.xCoord.toFloat(),
            realPos.yCoord.toFloat(),
            realPos.zCoord.toFloat(),
            ActiveRenderInfo.modelview,
            ActiveRenderInfo.projection,
            ActiveRenderInfo.viewport,
            screenPos
            )

        val x = screenPos.get(0) / it.res.scaleFactor
        val y = (mc.displayHeight - screenPos.get(1)) / it.res.scaleFactor
        val z = screenPos.get(2)
        if (x < mc.displayWidth && x > 0 && y < mc.displayHeight && y > 0 && z < 1) {
            val str = "${x.toInt()} ${y.toInt()} ${z.toInt()}"
            it.font.drawStringWithShadow(str, x.toInt() - (it.font.getStringWidth(str) / 2), y.toInt() - (it.font.FONT_HEIGHT / 2), -1)
        }
    }

    init {
        EventBus.addListener(renderOverlayEvent, RenderOverlayEvent::class)
    }

}