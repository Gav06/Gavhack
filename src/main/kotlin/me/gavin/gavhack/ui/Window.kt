package me.gavin.gavhack.ui

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.ui.api.GuiComponent
import me.gavin.gavhack.ui.api.IComponent
import me.gavin.gavhack.ui.api.Rect
import net.minecraft.client.gui.Gui

class Window(x: Int, y: Int, w: Int, h: Int,
             var title: String,
             val category: ModCategory,
             val closeable: Boolean = false): GuiComponent(x, y, w, h) {

    private val defaultHandleHeight = 16

    val handle = WindowHandle(x, y, w, defaultHandleHeight, this)
    val components = ArrayList<GuiComponent>()
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


        var fullHeight = h
        if (open) {
            components.forEach {
                fullHeight += it.getRenderHeight() + 1
            }
        }

        Gui.drawRect(left() - 1, top() - 1, right() + 1, y + fullHeight + 1, 0x80000000.toInt())
        handle.onRender(mouseX, mouseY, partialTicks)

        if (!open)
            return

        var yOffset = this.h + 1
        for (comp in components) {
            comp.setPos(this.x, this.y + yOffset)
            comp.onRender(mouseX, mouseY, partialTicks)
            yOffset += comp.getRenderHeight() + 1
        }
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        handle.keyTyped(keyChar, keyCode)
    }

}