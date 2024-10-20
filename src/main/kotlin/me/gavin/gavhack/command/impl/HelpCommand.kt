package me.gavin.gavhack.command.impl

import me.gavin.gavhack.command.Command
import me.gavin.gavhack.command.CommandManager
import net.minecraft.util.EnumChatFormatting

class HelpCommand: Command("help", "Shows a list of commands", "", "?") {

    override fun execute(command: String, args: Array<String>): Boolean {
        message("===== Gavhack Command Menu =====")
        for (cmd in CommandManager.commands) {
            message("${green}${cmd.name}: ${EnumChatFormatting.RESET}${cmd.desc}")
        }
        message("===============================")

        return true
    }
}