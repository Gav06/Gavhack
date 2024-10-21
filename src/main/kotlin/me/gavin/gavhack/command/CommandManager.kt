package me.gavin.gavhack.command

import me.gavin.gavhack.command.impl.*
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ChatMessageEvent
import me.gavin.gavhack.event.events.KeyPressEvent
import me.gavin.gavhack.misc.Utils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiChat
import net.minecraft.util.EnumChatFormatting
import java.util.*

object CommandManager {

    const val CMD_PREFIX = '.'

    val commands : Array<Command> = arrayOf(
        InfoCommand(),
        HelpCommand(),
        SyntaxCommand(),
        SpawnTPCommand(),
        ToggleCommand(),
        GuiCommand()
    )

    private val chatListener = Listener<ChatMessageEvent> {(it as ChatMessageEvent)
        if (it.message.startsWith(CMD_PREFIX)) {
            it.cancelled = true
            executeCommand(it.message.substring(1))
        }
    }

    private val keyListener = Listener<KeyPressEvent> { (it as KeyPressEvent)
        if (it.keyChar == CMD_PREFIX) {
            Minecraft.getMinecraft().displayGuiScreen(GuiChat("$CMD_PREFIX"))
        }
    }

    fun executeCommand(text: String) {
        val split = text.trim().split("\\s+".toRegex()).toTypedArray()

        if (split.isEmpty())
            return

        val cmdName = split[0]
        val cmdArgs = Arrays.copyOfRange(split,1, split.size)

        val cmd : Command = try {
            commands.first { it.matchesInput(cmdName) }
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
        EventBus.addListener(keyListener, KeyPressEvent::class)
    }
}