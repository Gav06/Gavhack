package me.gavin.gavhack.macro

import me.gavin.gavhack.command.CommandManager
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import org.lwjgl.input.Keyboard

object MacroManager {

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
}