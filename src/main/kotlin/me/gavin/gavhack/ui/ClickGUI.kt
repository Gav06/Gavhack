package me.gavin.gavhack.ui

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ClientTickEvent
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.ui.api.IComponent
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation

object ClickGUI: GuiScreen() {

    var previousGui: GuiScreen? = null
    val windows = ArrayList<Window>()

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

        if (mc.theWorld == null) {
            fontRendererObj.drawStringWithShadow("Press ESCAPE to go back", 2, 2, -1)
        }

        for (comp in windows)
            comp.onRender(mouseX, mouseY, partialTicks)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)

        for (comp in windows) {
            comp.onClick(mouseX, mouseY, button)
        }
    }

    override fun mouseMovedOrUp(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseMovedOrUp(mouseX, mouseY, button)

        for (comp in windows)
            comp.onRelease(mouseX, mouseY, button)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    fun setGuiShouldOpen() {
        shouldOpenGui = true
    }

    fun buttonClick() {
        mc.soundHandler.playSound(PositionedSoundRecord.getMasterRecord(ResourceLocation("gui.button.press"), 1.0f))
    }


    init {
        EventBus.addListener(livingTick, PlayerLivingTickEvent::class)

        var yOffset = 0
        for (category in ModCategory.entries) {
            // We can pass 0 as height since it will automatically be resized anyway.
            val window = Window(5, 5 + yOffset, 100, 0, category.title)
            window.open = false
            windows.add(window)
            yOffset += window.getTotalHeight() + 5
        }
    }
}