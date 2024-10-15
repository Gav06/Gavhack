package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.ScaledResolution

/* Called after all overlays are rendered; The last 2D overlay to be rendered in-game */
data class RenderOverlayEvent(val partialTicks: Float, val font: FontRenderer, val res: ScaledResolution) : Event()
