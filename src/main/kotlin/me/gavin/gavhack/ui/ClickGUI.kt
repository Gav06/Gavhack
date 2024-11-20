package me.gavin.gavhack.ui

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ClientTickEvent
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.ModuleManager
import me.gavin.gavhack.module.impl.GuiModule
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.FloatSetting
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import java.util.concurrent.CopyOnWriteArrayList

object ClickGUI: GuiScreen() {

    var previousGui: GuiScreen? = null
    val windows = CopyOnWriteArrayList<Window>()

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
        if (mc.theWorld == null)
            drawDefaultBackground()
        else if (GuiModule.drawBackground.value) {
            val rgba = 0x30ffffff
            drawRect(0, 0, width, height, rgba)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)

        if (mc.theWorld == null) {
            fontRendererObj.drawStringWithShadow("Press ESCAPE to go back", 2, height - 11, -1)
        }

        for (comp in windows.reversed())
            comp.onRender(mouseX, mouseY, partialTicks)


        // Draw le epic descriptions

        for (comp in windows) {
            if (comp.isInside(mouseX, mouseY))
                break

            for (button in comp.components) {
                if (!button.isInside(mouseX, mouseY))
                    continue

                val desc = button.module.desc
                this.func_146283_a(listOf(desc), mouseX + 5, mouseY + 5)
                return
            }
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)

        for (comp in windows) {
            val fullRect = Rect(comp.x, comp.y, comp.w, comp.getRenderHeight())
            if (fullRect.isInside(mouseX, mouseY)) {
                comp.onClick(mouseX, mouseY, button)
                // move it to front
                // only possible because of CopyOnWriteArrayList
                windows.remove(comp)
                windows.add(0, comp)
                break
            }
        }
    }

    override fun mouseMovedOrUp(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseMovedOrUp(mouseX, mouseY, button)

        for (comp in windows)
            comp.onRelease(mouseX, mouseY, button)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        super.keyTyped(keyChar, keyCode)

        for (comp in windows)
            comp.keyTyped(keyChar, keyCode)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    fun buttonClick() {
        mc.soundHandler.playSound(PositionedSoundRecord.getMasterRecord(ResourceLocation("gui.button.press"), 1.0f))
    }


    init {
        EventBus.addListener(livingTick, PlayerLivingTickEvent::class)

        var xOffset = 0
        for (category in ModCategory.entries) {
            // We can pass 0 as height since it will automatically be resized anyway.
            val window = Window(4 + xOffset, 4, 100, 0, category.title, category)

            for (module in ModuleManager.getCategoryMods(category)) {
                val button = WindowButton(module, 0, 0, window.w, window.handle.h - 2)

                for (setting in module.settings) {
                    // add setting components
                    when (setting) {
                        is ToggleSetting -> button.subComponents.add(BooleanComponent(setting, button.w, button.h))
                        is FloatSetting -> button.subComponents.add(FloatComponent(setting, button.w, button.h))
                        is EnumSetting<*> -> button.subComponents.add(EnumComponent(setting, button.w, button.h))
                    }
                }

                button.subComponents.add(BindComponent(module, button.w, button.h))
                window.components.add(button)
            }

            windows.add(window)
            xOffset += window.w + 4
        }
    }
}