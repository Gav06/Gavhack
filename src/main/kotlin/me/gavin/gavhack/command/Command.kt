package me.gavin.gavhack.command

import me.gavin.gavhack.misc.Utils

abstract class Command(val name: String, val desc: String, vararg aliases: String) {

    abstract fun execute(command: String, args: Array<String>) : Boolean

    protected fun message(msg: String) {
        Utils.gameMessage(msg)
    }

}