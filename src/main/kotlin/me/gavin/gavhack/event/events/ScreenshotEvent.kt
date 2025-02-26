package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.client.shader.Framebuffer
import java.io.File

data class ScreenshotEvent(
    var dataDir: File,
    var width: Int, var height: Int,
    var framebuffer: Framebuffer
) : Event()
