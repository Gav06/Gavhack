package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.misc.ProjectionUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.renderer.ActiveRenderInfo
import net.minecraft.util.Vec3


object ESP : Module("ESP", "See entities through walls", ModCategory.RENDER) {

    private val renderOverlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (!isEnabled())
            return@Listener

        ProjectionUtils.updateMatrix()

        val worldPos = Vec3.createVectorHelper(0.0, 6.0, 0.0)
        val camPos = ActiveRenderInfo.projectViewFromEntity(mc.renderViewEntity, mc.timer.renderPartialTicks.toDouble())

        val pos = camPos.subtract(worldPos)
            
        if (screenCoord != null) {
            it.font.drawString("Test", screenCoord.x.toInt(), screenCoord.y.toInt(), -1)
        }
    }

    init {
        EventBus.addListener(renderOverlayEvent, RenderOverlayEvent::class)
    }

}