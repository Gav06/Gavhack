package me.gavin.gavhack.module.impl.hud

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.gui.api.Rect
import me.gavin.gavhack.module.HUDModule

object Watermark: HUDModule("Watermark", "Client branding") {

    private const val TEXT = "${Gavhack.NAME} ${Gavhack.VERSION_STRING}"

    override fun updateBounds(comp: Rect) {
        comp.setSize(mc.fontRenderer.getStringWidth(TEXT) + 2, mc.fontRenderer.FONT_HEIGHT + 2)
    }

    override fun drawComponent(comp: Rect, mouseX: Int, mouseY: Int, partialTicks: Float) {
        mc.fontRenderer.drawStringWithShadow(TEXT, comp.x + 1, comp.y + 1, Gavhack.colorScheme.rgb)
    }

}