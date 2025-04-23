package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.BooleanSetting
import me.gavin.gavhack.gui.click.ClickGUI
import org.lwjgl.input.Keyboard

object GUI: Module("GUI", "Configure the ClickGUI", ModCategory.GUI, Keyboard.KEY_RSHIFT) {

    val drawBackground = addSetting(BooleanSetting("Background", true))
    val drawDesc = addSetting(BooleanSetting("Descriptions", true))

    override fun onEnable() {
        mc.displayGuiScreen(ClickGUI)
        disable()
    }
}