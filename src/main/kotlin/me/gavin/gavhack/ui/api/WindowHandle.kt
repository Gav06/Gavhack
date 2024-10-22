package me.gavin.gavhack.ui.api

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui

class WindowHandle(x: Int, y: Int, w: Int, h: Int, val parent: Window): Draggable(x, y, w, h) {

    init {
        if (parent.closeable) {
            TODO("Implement closable windows later lol")
        }
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.onRender(mouseX, mouseY, partialTicks)

        Gui.drawRect(left(), top(), right(), bottom(), 0xAA000000.toInt())
        val font = Minecraft.getMinecraft().fontRenderer
        font.drawStringWithShadow(parent.title,
            w /2 - font.getStringWidth(parent.title) / 2,
            h / 2 - font.FONT_HEIGHT / 2, -1)
    }
}