package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.ToggleSetting
import me.gavin.gavhack.gui.ClickGUI
import me.gavin.gavhack.module.setting.IntSetting
import org.lwjgl.input.Keyboard

object GUI: Module("GUI", "Configure the ClickGUI", ModCategory.GUI, Keyboard.KEY_RSHIFT) {

    val drawBackground = addSetting(ToggleSetting("Background", true))
    val drawDesc = addSetting(ToggleSetting("Descriptions", true))
    val blurSize = addSetting(IntSetting("BlurRadius", 25, 1, 100))

    override fun onEnable() {
        mc.displayGuiScreen(ClickGUI)
        disable()
    }
}