package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.ToggleSetting
import me.gavin.gavhack.ui.ClickGUI
import org.lwjgl.input.Keyboard

object GuiModule: Module("ClickGUI", "Configure the ClickGUI", ModCategory.GUI, Keyboard.KEY_RSHIFT) {

    val drawBackground = addSetting(ToggleSetting("Background", true))

    override fun onEnable() {
        mc.displayGuiScreen(ClickGUI)
        disable()
    }
}