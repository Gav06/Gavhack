package me.gavin.gavhack.gui.hud

import me.gavin.gavhack.config.ConfigManager
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.gui.click.Window
import me.gavin.gavhack.gui.click.WindowButton
import me.gavin.gavhack.module.HUDModule
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.ModuleManager
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen

object HUDEditor: GuiScreen() {

    private val window = Window(4, 4, 100, 0, "Components", ModCategory.HUD)
    private val hudMods = ModuleManager.getCategoryMods(ModCategory.HUD).filterIsInstance<HUDModule>()

    private val overlayListener = Listener<RenderOverlayEvent> { (it as RenderOverlayEvent)
        val mc = Minecraft.getMinecraft()
        if (mc.currentScreen != null && mc.currentScreen is HUDEditor)
            return@Listener

        hudMods
            .filter { m -> m.isEnabled() }
            .forEach{m -> m.drawComponent(m.getComponent(), 0, 0, it.partialTicks) }
    }

    init {
        EventBus.addListener(overlayListener, RenderOverlayEvent::class)

        for (mod in hudMods) {
            window.components.add(WindowButton(mod, 0, 0, window.w, window.handle.h - 2))
        }

        ConfigManager.addConfig(window)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
        window.onRender(mouseX, mouseY, partialTicks)

        hudMods
            .filter { it.isEnabled() }
            .forEach { it.onRender(mouseX, mouseY, partialTicks) }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)

        window.onClick(mouseX, mouseY, button)

        hudMods
            .filter { it.isEnabled() }
            .forEach { it.getComponent().onClick(mouseX, mouseY, button) }
    }

    override fun mouseMovedOrUp(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseMovedOrUp(mouseX, mouseY, button)

        window.onRelease(mouseX, mouseY, button)

        hudMods
            .filter { it.isEnabled() }
            .forEach { it.getComponent().onRelease(mouseX, mouseY, button) }

    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }
}