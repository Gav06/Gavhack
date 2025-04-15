package me.gavin.gavhack.gui

import me.gavin.gavhack.module.Module
import me.gavin.gavhack.gui.api.GuiComponent
import me.gavin.gavhack.utilities.Utils
import net.minecraft.client.gui.Gui
import org.lwjgl.input.Keyboard

/* Used for creating macros to bind modules to key presses */
class BindComponent(val module: Module, w: Int, h: Int): GuiComponent(0, 0, w, h) {

    private var binding = false

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        if (!isInside(mouseX, mouseY))
            return

        when (button) {
            0 -> {
                binding = !binding;
                Utils.clickSound()
            }
            1 -> {
                module.bind = 0
                Utils.clickSound()
                binding = false
            }
        }
    }

    // No need for this
    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) { }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (isInside(mouseX, mouseY)) {
            Gui.drawRect(left(), top(), right(), bottom(), 0x20ffffff)
        }

        val text = if (binding) "Press a key..." else "Bind: ${Keyboard.getKeyName(module.bind)}"
        getFont().drawString(text, x + 2, y + (h / 2) - (getFont().FONT_HEIGHT / 2), -1)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        if (!binding || keyCode == 0)
            return

        module.bind = if (keyCode == Keyboard.KEY_DELETE) 0 else keyCode
        Utils.clickSound()
        binding = false
    }
}