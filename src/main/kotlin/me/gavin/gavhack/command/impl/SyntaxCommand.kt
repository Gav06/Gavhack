package me.gavin.gavhack.command.impl

import me.gavin.gavhack.command.Command
import me.gavin.gavhack.command.CommandManager

class SyntaxCommand: Command("syntax", "Shows command syntax", "<command>", "command") {

    override fun execute(command: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            return false
        }


        val target : Command? = CommandManager.commands.firstOrNull { it.matchesInput(args[0]) }

        if (target == null) {
            error("Unable to find the specified command")
        } else {
            message("Command syntax: " +
                    "$green${CommandManager.CMD_PREFIX}${target.name} ${target.syntax}")
        }

        return true
    }
}