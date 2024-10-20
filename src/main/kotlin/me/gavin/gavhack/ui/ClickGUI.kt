package me.gavin.gavhack.ui

import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.gui.GuiScreen

object ClickGUI: GuiScreen() {

    var previousGui: GuiScreen? = null

    override fun keyTyped(par1: Char, par2: Int) {
        if (previousGui != null && previousGui is GuiMainMenu) {
            mc.displayGuiScreen(previousGui)
            previousGui = null
        } else {
            super.keyTyped(par1, par2)
        }
    }
}