package me.gavin.gavhack.command

import me.gavin.gavhack.command.commands.*
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ChatMessageEvent
import me.gavin.gavhack.misc.Utils

object CommandManager {

    const val CMD_PREFIX = '.'

    val commands : Array<Command> = arrayOf(
        InfoCommand()
    )

    private val chatListener = Listener<ChatMessageEvent>() {(it as ChatMessageEvent)
        if (it.message.startsWith(CMD_PREFIX)) {
            it.cancelled = true
            Utils.gameMessage("Ayo this dude just tried to type a command")
        }
    }

    init {
        EventBus.addListener(chatListener, ChatMessageEvent::class)
    }
}