package me.gavin.gavhack.command.commands

import me.gavin.gavhack.command.Command
import me.gavin.gavhack.command.CommandManager
import net.minecraft.util.EnumChatFormatting

class HelpCommand: Command("help", "Shows a list of commands", "", "?") {

    override fun execute(command: String, args: Array<String>): Boolean {
        message("Gavhack command menu:")
        for (cmd in CommandManager.commands) {
            message("${green}${cmd.name}: ${EnumChatFormatting.RESET}${cmd.desc}")
        }
        return true
    }
}