package me.gavin.gavhack.command.impl

import me.gavin.gavhack.command.Command
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.ModuleManager

class ToggleCommand: Command("toggle", "Toggle a given module", "<module>", "t"){
    override fun execute(command: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            return false
        }


        val target : Module? = ModuleManager.mods.firstOrNull { it.name.equals(args[0], true) }

        if (target == null) {
            error("Unable to find the specified module")
        } else {
            target.toggle()
        }

        return true
    }

}
