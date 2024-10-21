package me.gavin.gavhack.command.impl

import me.gavin.gavhack.command.Command
import me.gavin.gavhack.ui.ClickGUI

class GuiCommand: Command("clickgui", "Opens the Click GUI", "", "gui", "opengui") {

    override fun execute(command: String, args: Array<String>): Boolean {
        ClickGUI.setGuiShouldOpen()
        return true
    }
}