package me.gavin.gavhack.command.commands

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.command.Command
import me.gavin.gavhack.command.CommandManager
import me.gavin.gavhack.module.ModuleManager
import net.minecraft.util.EnumChatFormatting

class InfoCommand : Command("info", "Provides information about your game", "", "i") {
    override fun execute(command: String, args: Array<String>) : Boolean {
        val numMods = ModuleManager.mods.size
        val numCmds = CommandManager.commands.size

        message("${green}Gavhack stats:")
        message("Client version $green${Gavhack.VER_STR}")
        message("Number of modules $green$numMods")
        message("Number of commands $green$numCmds")
        return true
    }


}