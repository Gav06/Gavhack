package me.gavin.gavhack.ui

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent

object OverlayUI {

    private val overlayListener = Listener<RenderOverlayEvent> { e -> (e as RenderOverlayEvent)
        e.font.drawStringWithShadow("Hello from kotlin!", 2, 2, -1)
    }

    init {
        EventBus.addListener(overlayListener, RenderOverlayEvent::class)
    }

}