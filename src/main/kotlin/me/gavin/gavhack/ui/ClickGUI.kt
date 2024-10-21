package me.gavin.gavhack.ui

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ClientTickEvent
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.ui.api.IComponent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.gui.GuiScreen

object ClickGUI: GuiScreen() {

    var previousGui: GuiScreen? = null
    val components = ArrayList<IComponent>()

    private var shouldOpenGui = false

    /* Hacky way to get gui to open after typing the command in the chat box */
    private val livingTick = Listener<ClientTickEvent> {
        val minecraft = Minecraft.getMinecraft()
        if (minecraft != null && shouldOpenGui && minecraft.currentScreen == null) {
            shouldOpenGui = false
            minecraft.displayGuiScreen(ClickGUI)
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)

        if (previousGui != null) {
            fontRendererObj.drawStringWithShadow("Press ESCAPE to go back", 2, 2, -1)
        }

        for (comp in components)
            comp.onRender(mouseX, mouseY, partialTicks)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)

        for (comp in components)
            comp.onClick(mouseX, mouseY, button)
    }

    override fun mouseMovedOrUp(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseMovedOrUp(mouseX, mouseY, button)

        for (comp in components)
            comp.onRelease(mouseX, mouseY, button)
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

    fun setGuiShouldOpen() {
        shouldOpenGui = true
    }

    init {
        EventBus.addListener(livingTick, PlayerLivingTickEvent::class)

        components.add(TestComponent(10, 10, 100, 100))
    }
}