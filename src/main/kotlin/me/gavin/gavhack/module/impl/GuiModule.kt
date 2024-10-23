package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.ui.ClickGUI

object GuiModule: Module("ClickGUI", "Configure the ClickGUI", ModCategory.GUI) {

    override fun onEnable() {
        mc.displayGuiScreen(ClickGUI)
        disable()
    }
}