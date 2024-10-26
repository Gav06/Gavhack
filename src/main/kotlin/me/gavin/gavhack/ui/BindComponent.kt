package me.gavin.gavhack.ui

import me.gavin.gavhack.macro.Macro
import me.gavin.gavhack.macro.MacroManager
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.ui.api.GuiComponent
import org.lwjgl.input.Keyboard

/* Used for creating macros to bind modules to key presses */
class BindComponent(val module: Module, x: Int, y: Int, w: Int, h: Int): GuiComponent(x, y, w, h) {

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        TODO("Not yet implemented")
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        TODO("Not yet implemented")
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val macro: Macro? = MacroManager.searchByCommand("toggle ${module.name}")

        val text = if (macro != null) "Bind: ${Keyboard.getKeyName(macro.key)}" else "Bind: NOT SET"
        getFont().drawString(text, x + 2, y + (h / 2) - (getFont().FONT_HEIGHT / 2), -1)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        TODO("Not yet implemented")
    }
}