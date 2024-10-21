package me.gavin.gavhack.ui

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.gui.GuiScreen

object ClickGUI: GuiScreen() {

    var previousGui: GuiScreen? = null

    private var shouldOpenGui = false

    /* Hacky way to get gui to open after typing the command in the chat box */
    private val livingTick = Listener<PlayerLivingTickEvent> {
        if (mc.currentScreen == null && shouldOpenGui) {
            shouldOpenGui = false
            mc.displayGuiScreen(this)
        }
    }

    override fun keyTyped(par1: Char, par2: Int) {
        if (previousGui != null && previousGui is GuiMainMenu) {
            mc.displayGuiScreen(previousGui)
            previousGui = null
        } else {
            super.keyTyped(par1, par2)
        }
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }


    fun setGuiShouldOpen() {
        shouldOpenGui = true
    }

    init {
        EventBus.addListener(livingTick, PlayerLivingTickEvent::class)
    }
}