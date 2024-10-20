package me.gavin.gavhack.command.impl

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.command.Command
import me.gavin.gavhack.command.CommandManager
import me.gavin.gavhack.module.ModuleManager

class InfoCommand : Command("info", "Provides information about your game", "", "i") {
    override fun execute(command: String, args: Array<String>) : Boolean {
        val numMods = ModuleManager.mods.size
        val numCmds = CommandManager.commands.size

        message("${green}Gavhack Stats:")
        message("Client version $green${Gavhack.VERSION_STRING}")
        message("Number of modules $green$numMods")
        message("Number of commands $green$numCmds")
        return true
    }


}