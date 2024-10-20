package me.gavin.gavhack.module

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import me.gavin.gavhack.module.impl.*
import net.minecraft.client.Minecraft
import org.lwjgl.input.Keyboard

object ModuleManager {

    /* In alphabetic order by default */
    val mods = arrayOf(
        TestModule,
        Fullbright
    )

    /* Sorted by length of module name */
    val modsSorted = mods.copyOf()

    private val keyListener = Listener<KeyPressEvent> { (it as KeyPressEvent)
        if (it.key == Keyboard.KEY_NONE)
            return@Listener

        mods.forEach { m ->
            if (m.bind == it.key)
                m.toggle()
        }
    }

    init {
        /*
        Add Event Listener
         */
        EventBus.addListener(keyListener, KeyPressEvent::class)


        /* Sort module lists for proper display */
        mods.sortBy { it.name }

        val font = Minecraft.getMinecraft().fontRenderer
        modsSorted.sortBy { font.getStringWidth(it.name) }
    }
}