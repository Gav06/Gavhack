package me.gavin.gavhack.gui.click

import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.gui.api.GuiComponent
import me.gavin.gavhack.utilities.Utils
import net.minecraft.client.gui.Gui

class EnumComponent(val setting: EnumSetting<*>, w: Int, h: Int) : GuiComponent(0, 0, w, h) {

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        if (!isInside(mouseX, mouseY))
            return

        when (button) {
            0 -> { setting.increment(); Utils.clickSound() }
            1 -> { setting.decrement(); Utils.clickSound() }
        }
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) { }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (isInside(mouseX, mouseY)) {
            Gui.drawRect(left(), top(), right(), bottom(), 0x20ffffff)
        }

        getFont().drawString("${setting.name}: ${setting.enumValue()}",
            x + 2, y + (h / 2) - (getFont().FONT_HEIGHT / 2), -1)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) { }
}