package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.misc.ProjectionUtils
import me.gavin.gavhack.misc.RenderUtils
import net.minecraft.util.Vec3

object ESP : Module("ESP", "See entities through walls", ModCategory.RENDER) {

    private val renderOverlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (!isEnabled())
            return@Listener

        for (e in mc.theWorld.loadedEntityList) {
            if (e == mc.renderViewEntity)
                continue

            val screenCoord = ProjectionUtils.toScaledScreenPos(Vec3.createVectorHelper(e.posX, e.posY, e.posZ))

//            if (screenCoord.zCoord == -1.0)
//                return@Listener

            it.font.drawString(e.commandSenderName, screenCoord.xCoord.toInt(), screenCoord.yCoord.toInt(), -1)
        }
    }

    init {
        EventBus.addListener(renderOverlayEvent, RenderOverlayEvent::class)
    }
}