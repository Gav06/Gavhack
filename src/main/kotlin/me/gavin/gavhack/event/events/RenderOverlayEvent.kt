package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.ScaledResolution

data class RenderOverlayEvent(val partialTicks: Float, val font: FontRenderer, val res: ScaledResolution) : Event()
