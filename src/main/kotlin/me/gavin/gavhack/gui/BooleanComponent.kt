package me.gavin.gavhack.gui

import me.gavin.gavhack.module.setting.ToggleSetting
import me.gavin.gavhack.gui.api.GuiComponent
import net.minecraft.client.gui.Gui
import net.minecraft.util.EnumChatFormatting

class BooleanComponent(val setting: ToggleSetting, w: Int, h: Int) : GuiComponent(0, 0, w, h) {

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        if (!isInside(mouseX, mouseY))
            return

        setting.toggle()
        ClickGUI.buttonClick()
    }

    // unused
    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) { }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (isInside(mouseX, mouseY)) {
            Gui.drawRect(left(), top(), right(), bottom(), 0x20ffffff)
        }

        // makes text green/red if true/false
        val text = "${setting.name}: " +
                "${if (setting.value) EnumChatFormatting.GREEN else EnumChatFormatting.RED}" +
                if (setting.value) "on" else "off"

        getFont().drawString(text, x + 2, y + (h / 2) - (getFont().FONT_HEIGHT / 2), -1)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) { }


}