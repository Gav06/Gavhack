package me.gavin.gavhack.gui

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.ModuleManager
import net.minecraft.client.gui.GuiScreen

object HudGUI: GuiScreen() {

    val window = Window(4, 4, 100, 0, "Components", ModCategory.HUD)

    init {
        for (mod in ModuleManager.getCategoryMods(ModCategory.HUD)) {
            window.components.add(WindowButton(mod, 0, 0, window.w, window.handle.h - 2))
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)

        window.onRender(mouseX, mouseY, partialTicks)
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