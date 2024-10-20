package me.gavin.gavhack.command

import me.gavin.gavhack.misc.Utils
import net.minecraft.client.Minecraft
import net.minecraft.util.EnumChatFormatting

abstract class Command(
    val name: String,
    val desc: String,
    val syntax: String,
    private vararg val aliases: String) {

    protected val mc = Minecraft.getMinecraft()
    protected val green = EnumChatFormatting.GREEN.toString()

    abstract fun execute(command: String, args: Array<String>) : Boolean

    protected fun message(msg: String) {
        Utils.gameMessage(msg)
    }

    protected fun error(msg: String) {
        Utils.gameMessage("${EnumChatFormatting.RED}$msg")
    }

    fun matchesInput(input: String) : Boolean {
        for (alias in this.aliases) {
            if (input.equals(alias, true))
                return true
        }

        return input.equals(this.name, true)
    }

}