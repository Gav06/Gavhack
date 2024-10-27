package me.gavin.gavhack.module

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import me.gavin.gavhack.module.impl.*
import net.minecraft.client.Minecraft

object ModuleManager {

    /* In alphabetic order by default */
    val mods : Array<Module> = arrayOf(
        Fullbright,
        GuiModule,
        HudModule,
        ConsoleModule
    )

    /* Sorted by length of module name */
    val modsSorted = mods.copyOf()

    private val keyListener = Listener<KeyPressEvent> { (it as KeyPressEvent)
        mods.forEach { mod -> if (mod.bind == it.key && it.key != 0) mod.toggle() }
    }

    fun getCategoryMods(category: ModCategory) : List<Module> {
        return mods.filter { it.category == category }.toList()
    }

    init {
        EventBus.addListener(keyListener, KeyPressEvent::class)

        /* Sort module lists for proper display */
        mods.sortBy { it.name }

        val font = Minecraft.getMinecraft().fontRenderer
        // Using the negative value so that our array is from longest to shortest
        modsSorted.sortBy { -font.getStringWidth(it.name) }
    }
}