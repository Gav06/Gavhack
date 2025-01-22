package me.gavin.gavhack.module.impl

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.ModuleManager
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiChat
import net.minecraft.util.EnumChatFormatting

object HudModule: Module("HUD", "Configure the HUD", ModCategory.GUI) {

    private val watermark = this.addSetting(ToggleSetting("Watermark", true))
    private val modList = this.addSetting(ToggleSetting("ModList", true))
    private val position = this.addSetting(ToggleSetting("Position", true))

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

        if (position.value) {
            // co-ordinates
            var textY = it.res.scaledHeight - it.font.FONT_HEIGHT - 1

            if (mc.currentScreen is GuiChat)
                textY -= 14

            // Shorten our nums into 1 decimal place
            val x = "%.1f".format(mc.thePlayer.posX)
            val y = "%.1f".format(mc.thePlayer.posY)
            val z = "%.1f".format(mc.thePlayer.posZ)

            var str = "$x $y $z"
            val dim = mc.thePlayer.dimension

            // If we aren't in the end dimension
            if (dim != 1) {

                val altX = "%.1f".format(if (dim == 0) mc.thePlayer.posX / 8 else mc.thePlayer.posX * 8)
                val altY = "%.1f".format(if (dim == 0) mc.thePlayer.posY / 8 else mc.thePlayer.posY * 8)
                val altZ = "%.1f".format(if (dim == 0) mc.thePlayer.posZ / 8 else mc.thePlayer.posZ * 8)

                str += "${EnumChatFormatting.GRAY} $altX $altY $altZ"
            }

            it.font.drawStringWithShadow(str, 2, textY, -1)

            // facing direction

            it.font.drawStringWithShadow("", 2, textY - 12, -1)
        }
    }

    init {
        this.addEventListener(overlayEvent, RenderOverlayEvent::class)
    }

    override fun firstTimeSetup() {
        this.enable()
    }
}