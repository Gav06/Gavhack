package me.gavin.gavhack.ui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.module.ModuleManager
import me.gavin.gavhack.module.setting.EnumSetting
import net.minecraft.client.Minecraft
import org.lwjgl.input.Keyboard

object OverlayUI {

    private val mc = Minecraft.getMinecraft()

    private val overlayListener = Listener<RenderOverlayEvent> { (it as RenderOverlayEvent)
        if (mc.gameSettings.showDebugInfo)
            return@Listener

        it.font.drawString("${Gavhack.NAME} ${Gavhack.VERSION_STRING}",
                2, 2, Gavhack.colorScheme.rgb)

        var yOffset = 12
        for (mod in ModuleManager.modsSorted) {
            if (!mod.isEnabled())
                continue

            it.font.drawString(mod.name, 2, yOffset, -1)
            yOffset += it.font.FONT_HEIGHT + 1
        }

    }

    init {
        EventBus.addListener(overlayListener, RenderOverlayEvent::class)
    }
}