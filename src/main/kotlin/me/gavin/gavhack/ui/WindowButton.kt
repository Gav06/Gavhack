package me.gavin.gavhack.ui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.ui.api.GuiComponent
import me.gavin.gavhack.ui.api.IComponent
import net.minecraft.client.gui.Gui

class WindowButton(val module: Module, x: Int, y: Int, w: Int, h: Int): GuiComponent(x, y, w, h) {

    var open = false
    val subComponents = ArrayList<GuiComponent>()

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        if (isInside(mouseX, mouseY)) {
            when (button) {
                0 -> {
                    module.toggle()
                    ClickGUI.buttonClick()
                }
                1 -> {
                    this.open = !open
                    ClickGUI.buttonClick()
                }
            }
        }
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {

    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Gui.drawRect(left(), top(), right(), bottom(), 0x60000000)

        if (isInside(mouseX, mouseY)) {
            Gui.drawRect(left(), top(), right(), bottom(), 0x20ffffff)
        }

        val color = if (module.isEnabled()) Gavhack.colorScheme.rgb else -1
        getFont().drawString(module.name,
            x + (w / 2) - (getFont().getStringWidth(module.name) / 2),
            y + (h / 2) - (getFont().FONT_HEIGHT / 2), color)


        if (open) {
            var yOffset = 1
            for (comp in subComponents) {
                comp.setPos(this.x, this.bottom() + yOffset)
                comp.onRender(mouseX, mouseY, partialTicks)

                yOffset += comp.getRenderHeight() + 1
            }
        }
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {

    }

    override fun getRenderHeight(): Int {
        return if (open) {
            var height = this.h
            for (comp in subComponents) {
                height += 1 + comp.getRenderHeight()
            }
            return height
        } else {
            this.h
        }
    }
}