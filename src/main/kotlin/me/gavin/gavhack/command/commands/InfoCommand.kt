package me.gavin.gavhack.command.commands

import me.gavin.gavhack.command.Command

class InfoCommand : Command("info", "Provides information about your game", "i") {
    override fun execute(command: String, args: Array<String>) : Boolean {
        message("Command entered: $command, Args: $args")
        return true
    }


}