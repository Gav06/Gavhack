package me.gavin.gavhack.ui

import me.gavin.gavhack.module.Module
import me.gavin.gavhack.ui.api.GuiComponent
import me.gavin.gavhack.ui.api.IComponent
import net.minecraft.client.gui.Gui

class WindowButton(val module: Module, x: Int, y: Int, w: Int, h: Int): GuiComponent(x, y, w, h) {

    var open = false
    val subComponents = ArrayList<IComponent>()

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {

    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {

    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Gui.drawRect(left(), top(), right(), bottom(), 0x80000000.toInt())
        getFont().drawString(module.name, x + 2, y + (h / 2) - (getFont().FONT_HEIGHT / 2), -1)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {

    }

    override fun getRenderHeight(): Int {
        return if (open) {
            0
        } else {
            this.h
        }
    }
}