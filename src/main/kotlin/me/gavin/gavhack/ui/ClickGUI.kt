package me.gavin.gavhack.ui

import net.minecraft.client.gui.GuiScreen

object ClickGUI: GuiScreen() {

    override fun drawDefaultBackground() {
        if (mc.theWorld == null)
            super.drawDefaultBackground()

    }
}