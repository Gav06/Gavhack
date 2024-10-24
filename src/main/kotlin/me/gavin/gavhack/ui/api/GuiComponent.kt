package me.gavin.gavhack.ui.api

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer

abstract class GuiComponent(x: Int, y: Int, w: Int, h: Int): Rect(x, y, w, h), IComponent {

    fun getFont(): FontRenderer = Minecraft.getMinecraft().fontRenderer

    open fun getRenderHeight() : Int {
        return this.h
    }

    open fun getRenderWidth() : Int {
        return this.w
    }
}