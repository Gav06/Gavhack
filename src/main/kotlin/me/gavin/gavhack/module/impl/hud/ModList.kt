package me.gavin.gavhack.module.impl.hud

import me.gavin.gavhack.gui.api.Rect
import me.gavin.gavhack.module.HUDModule
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.ModuleManager

object ModList: HUDModule("ModList", "Lists enabled modules") {

    // init empty list for any cases where the list is queried before it has data in it.
    private var drawnMods: List<Module> = listOf()

    override fun updateBounds(comp: Rect) {
        val h = (drawnMods.size * 10) + 2
        var w = 0
        if (drawnMods.isNotEmpty())
            w = mc.fontRenderer.getStringWidth(drawnMods[0].name)

        comp.setSize(w, h)
    }

    override fun drawComponent(comp: Rect, mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawnMods = ModuleManager.modsSorted
            .filter { m -> m.isEnabled() }
            .filter { m -> m.category != ModCategory.HUD }

        var yOffset = 0
        drawnMods.forEach { m ->
            mc.fontRenderer.drawStringWithShadow(
                m.name,
                comp.x + 1, comp.y + yOffset + 1,
                ModuleManager.getModuleColor(m)
            )
            yOffset += 10
        }
    }
}