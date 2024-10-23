package me.gavin.gavhack.ui

import me.gavin.gavhack.ui.api.IComponent
import me.gavin.gavhack.ui.api.Rect
import net.minecraft.client.gui.Gui

class Window(x: Int, y: Int, w: Int, h: Int, var title: String, val closeable: Boolean = false): Rect(x, y, w, h),
    IComponent {

    private val defaultHandleHeight = 12

    val handle = WindowHandle(x, y, w, defaultHandleHeight, this)
    var open = false

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        handle.onClick(mouseX, mouseY, button)
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        handle.onRelease(mouseX, mouseY, button)
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        handle.updateDragPosition(mouseX, mouseY)
        this.setPos(handle.x, handle.y)
        this.setSize(this.w, handle.h)

        Gui.drawRect(left() - 1, top() - 1, right() + 1, bottom() + 1, 0xAA000000.toInt())
        handle.onRender(mouseX, mouseY, partialTicks)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        handle.keyTyped(keyChar, keyCode)
    }

    fun getTotalHeight() : Int {
        if (open) {
            return h
        } else {
            return handle.h
        }
    }
}