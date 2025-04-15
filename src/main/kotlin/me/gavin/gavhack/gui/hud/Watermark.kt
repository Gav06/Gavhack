package me.gavin.gavhack.gui.hud

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.gui.HUDModule

object Watermark: HUDModule("Watermark", "Client branding") {

    override fun drawComponent(mouseX: Int, mouseY: Int, partialTicks: Float, isGui: Boolean) {
        mc.fontRenderer.drawStringWithShadow("${Gavhack.NAME} ${Gavhack.VERSION_STRING}",
            2, 2, Gavhack.colorScheme.rgb)
    }

}