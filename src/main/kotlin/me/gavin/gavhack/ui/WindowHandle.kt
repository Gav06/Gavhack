package me.gavin.gavhack.ui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.ui.api.Draggable
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui


class WindowHandle(x: Int, y: Int, w: Int, h: Int, val parent: Window): Draggable(x, y, w, h) {

    init {
        if (parent.closeable) {
            TODO("Implement closable windows later lol")
        }
    }

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        super.onClick(mouseX, mouseY, button)

        if (isInside(mouseX, mouseY) && button == 1) {
            parent.open = !parent.open
        }
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
//        Gui.drawRect(left(), top(), right(), bottom(), )
        val font = Minecraft.getMinecraft().fontRenderer
        font.drawStringWithShadow(parent.title,
            x + (w /2) - (font.getStringWidth(parent.title) / 2),
            y + (h / 2) - (font.FONT_HEIGHT / 2), -1)

        val s = if (parent.open) "." else "..."
        font.drawStringWithShadow(s, x + w - font.getStringWidth(s) - 1,
                y + (h / 2) - (font.FONT_HEIGHT / 2), -1)
    }
}