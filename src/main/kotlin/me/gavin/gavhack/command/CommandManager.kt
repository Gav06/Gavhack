package me.gavin.gavhack.command

import com.google.common.collect.Lists
import me.gavin.gavhack.command.commands.*
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ChatMessageEvent
import me.gavin.gavhack.misc.Utils
import java.util.*
import kotlin.NoSuchElementException

object CommandManager {

    const val CMD_PREFIX = '.'

    val commands : Array<Command> = arrayOf(
        InfoCommand()
    )

    private val chatListener = Listener<ChatMessageEvent> {(it as ChatMessageEvent)
        if (it.message.startsWith(CMD_PREFIX)) {
            it.cancelled = true
            runCommand(it.message)
        }
    }

    fun runCommand(text: String) {
        val result = parseCommand(text)
        // handle result

    }

    private fun parseCommand(text: String) : Boolean {
        val split = text.trim().substring(1).split("\\s+".toRegex()).toTypedArray()

        if (split.isEmpty())
            return false

        val cmdName = split[0]
        val cmdArgs = Arrays.copyOfRange(split,1, split.size)

        Utils.gameMessage("name: $cmdName args: ${cmdArgs.contentToString()}")


        val cmd : Command = try {
            commands.first { it.name.equals(cmdName, true) }
        } catch (e: NoSuchElementException) { return false }

        return cmd.execute(cmdName, cmdArgs)
    }

    init {
        EventBus.addListener(chatListener, ChatMessageEvent::class)
    }
}