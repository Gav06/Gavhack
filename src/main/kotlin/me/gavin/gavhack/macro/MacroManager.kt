package me.gavin.gavhack.macro

import com.google.gson.JsonObject
import me.gavin.gavhack.command.CommandManager
import me.gavin.gavhack.config.IConfigurable
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import org.lwjgl.input.Keyboard

object MacroManager: IConfigurable {

    val macros = ArrayList<Macro>()

    private val keyListener = Listener<KeyPressEvent> { (it as KeyPressEvent)
        if (it.key != Keyboard.KEY_NONE) {
            macros.forEach { m ->
                if (m.key == it.key)
                    CommandManager.executeCommand(m.command)
            }
        }
    }

    init {
        EventBus.addListener(keyListener, KeyPressEvent::class)
    }

    override fun toJsonObj(): JsonObject {
        TODO("Not yet implemented")
    }

    override fun fromJsonObj(json: JsonObject) {
        TODO("Not yet implemented")
    }

    fun searchByCommand(command: String) : Macro? {
        var macro: Macro? = null
        for (m in macros) {
            if (m.command.equals(command, true)) {
                macro = m
                break
            }
        }

        return macro
    }
}