package me.gavin.gavhack.ui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import net.minecraft.client.Minecraft

object OverlayUI {

    private val mc = Minecraft.getMinecraft()

    private val overlayListener = Listener<RenderOverlayEvent> { (it as RenderOverlayEvent)
        if (mc.gameSettings.showDebugInfo)
            return@Listener

        it.font.drawStringWithShadow("${Gavhack.NAME} ${Gavhack.VER_STR}",
                2, 2, Gavhack.colorScheme.rgb)
    }

    init {
        EventBus.addListener(overlayListener, RenderOverlayEvent::class)
    }

}