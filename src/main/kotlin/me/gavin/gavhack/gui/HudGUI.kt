package me.gavin.gavhack.gui

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.ModuleManager
import net.minecraft.client.gui.GuiScreen

object HudGUI: GuiScreen() {

    private val window = Window(4, 4, 100, 0, "Components", ModCategory.HUD)
    private val hudMods = ModuleManager.getCategoryMods(ModCategory.HUD).filterIsInstance<HUDModule>()

    private val overlayListener = Listener<RenderOverlayEvent> { (it as RenderOverlayEvent)
        for (mod in hudMods) {
            if (mod.isEnabled()) mod.drawComponent(0, 0, it.partialTicks, false)
        }
    }

    init {
        EventBus.addListener(overlayListener, RenderOverlayEvent::class)

        for (mod in hudMods) {
            window.components.add(WindowButton(mod, 0, 0, window.w, window.handle.h - 2))
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)

        window.onRender(mouseX, mouseY, partialTicks)

        for (mod in hudMods) {
            if (!mod.isEnabled())
                continue

            mod.drawComponent(mouseX, mouseY, partialTicks, true)
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)

        window.onClick(mouseX, mouseY, button)
    }

    override fun mouseMovedOrUp(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseMovedOrUp(mouseX, mouseY, button)

        window.onRelease(mouseX, mouseY, button)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }
}