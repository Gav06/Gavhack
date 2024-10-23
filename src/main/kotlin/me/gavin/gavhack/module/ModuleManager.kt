package me.gavin.gavhack.module

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.module.impl.*
import net.minecraft.client.Minecraft

object ModuleManager {

    /* In alphabetic order by default */
    val mods = arrayOf(
            Fullbright,
            TestModule(Gavhack.randomString()),
            TestModule(Gavhack.randomString()),
            TestModule(Gavhack.randomString()),
            TestModule(Gavhack.randomString()),
            TestModule(Gavhack.randomString()),
            TestModule(Gavhack.randomString()),
            TestModule(Gavhack.randomString()),
            TestModule(Gavhack.randomString())
    )

    /* Sorted by length of module name */
    val modsSorted = mods.copyOf()

    fun getCategoryMods(category: ModCategory) : List<Module> {
        return mods.filter { it.category == category }.toList()
    }

    init {
        /* Sort module lists for proper display */
        mods.sortBy { it.name }

        val font = Minecraft.getMinecraft().fontRenderer
        // Using the negative value so that our array is from longest to shortest
        modsSorted.sortBy { -font.getStringWidth(it.name) }
    }
}