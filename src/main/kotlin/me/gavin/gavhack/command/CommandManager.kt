package me.gavin.gavhack.command

import me.gavin.gavhack.command.commands.HelpCommand
import me.gavin.gavhack.command.commands.InfoCommand
import me.gavin.gavhack.command.commands.SpawnTPCommand
import me.gavin.gavhack.command.commands.SyntaxCommand
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ChatMessageEvent
import me.gavin.gavhack.misc.Utils
import net.minecraft.util.EnumChatFormatting
import java.util.*

object CommandManager {

    const val CMD_PREFIX = '.'

    val commands : Array<Command> = arrayOf(
        InfoCommand(),
        HelpCommand(),
        SyntaxCommand(),
        SpawnTPCommand()
    )

    private val chatListener = Listener<ChatMessageEvent> {(it as ChatMessageEvent)
        if (it.message.startsWith(CMD_PREFIX)) {
            it.cancelled = true
            parseCommand(it.message)
        }
    }

    private fun parseCommand(text: String) {
        val split = text.trim().substring(1).split("\\s+".toRegex()).toTypedArray()

        if (split.isEmpty())
            return

        val cmdName = split[0]
        val cmdArgs = Arrays.copyOfRange(split,1, split.size)

        val cmd : Command = try {
            commands.first { it.name.equals(cmdName, true) }
        } catch (e: NoSuchElementException) {
            Utils.gameMessage(EnumChatFormatting.RED.toString() +
                    "Unknown command, use ${CMD_PREFIX}help to see a list of commands.")
            return
        }

        if (!cmd.execute(cmdName, cmdArgs)) {
            Utils.gameMessage(EnumChatFormatting.RED.toString() + "Command syntax invalid," +
                    " use ${CMD_PREFIX}syntax <command> to see use.")
        }
    }

    init {
        EventBus.addListener(chatListener, ChatMessageEvent::class)
    }
}