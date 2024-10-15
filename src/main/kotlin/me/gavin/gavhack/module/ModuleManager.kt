package me.gavin.gavhack.module

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import net.minecraft.client.Minecraft
import org.lwjgl.input.Keyboard

//import me.gavin.gavhack.module.combat.*
//import me.gavin.gavhack.module.movement.*
//import me.gavin.gavhack.module.render.*
//import me.gavin.gavhack.module.world.*
//import me.gavin.gavhack.module.misc.*

object ModuleManager {

    /* In alphabetic order by default */
    val mods = ArrayList<Module>()
    /* Sorted by length of module name */
    val modsSorted = ArrayList<Module>()

    private val keyListener = Listener<KeyPressEvent> { (it as KeyPressEvent)
        if (it.key == Keyboard.KEY_NONE)
            return@Listener

        mods.forEach { m ->
            if (m.bind == it.key)
                m.toggle()
        }
    }
    
    private fun addModule(mod: Module) {
        mods.add(mod)
        modsSorted.add(mod)
    }

    init {
        EventBus.addListener(keyListener, KeyPressEvent::class)
        /* Register all modules here */



        /* Sort module lists for proper display */
        mods.sortBy { it.name }
        val font = Minecraft.getMinecraft().fontRenderer
        modsSorted.sortBy { font.getStringWidth(it.name) }
    }
}