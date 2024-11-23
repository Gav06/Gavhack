package me.gavin.gavhack.module.impl

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.ModuleManager
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.client.gui.Gui
import org.lwjgl.opengl.GL11

object HudModule: Module("HUD", "Configure the HUD", ModCategory.GUI) {

    private val watermark = this.addSetting(ToggleSetting("Watermark", true))
    private val modList = this.addSetting(ToggleSetting("ModList", true))

    private val overlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (mc.gameSettings.showDebugInfo)
            return@Listener

        if (watermark.value) {
            it.font.drawStringWithShadow("${Gavhack.NAME} ${Gavhack.VERSION_STRING}",
                    2, 2, Gavhack.colorScheme.rgb)
        }

        if (modList.value) {
            var yOffset = 2
            for (mod in ModuleManager.modsSorted) {
                if (!mod.isEnabled())
                    continue

                val textX = it.res.scaledWidth - it.font.getStringWidth(mod.name) - 1
                val textY = yOffset

                Gui.drawRect(
                    textX - 2, textY - 2, it.res.scaledWidth, textY + it.font.FONT_HEIGHT,
                    0x80000000.toInt()
                )
                it.font.drawString(mod.name, textX, textY, -1)
                yOffset += it.font.FONT_HEIGHT + 2
            }
        }
    }

    init {
        this.addEventListener(overlayEvent, RenderOverlayEvent::class)

        this.enable()
    }
}