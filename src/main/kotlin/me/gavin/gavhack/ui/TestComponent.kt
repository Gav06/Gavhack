package me.gavin.gavhack.ui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.ui.api.Draggable
import net.minecraft.client.gui.Gui

class TestComponent(x: Int, y: Int, w: Int, h: Int): Draggable(x, y, w, h) {

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.onRender(mouseX, mouseY, partialTicks)

        Gui.drawRect(x, y, x + w, y + h, Gavhack.colorScheme.rgb)
    }
}