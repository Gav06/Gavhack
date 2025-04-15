package me.gavin.gavhack.module

import me.gavin.gavhack.config.ConfigManager
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import me.gavin.gavhack.module.impl.*
import me.gavin.gavhack.module.impl.hud.Position
import me.gavin.gavhack.module.impl.hud.Watermark
import net.minecraft.client.Minecraft

object ModuleManager {

    /* Gets sorted to alphabetic order by default */
    val mods : Array<Module> = arrayOf(
        // hack modules
            Fullbright,
            GUI,
            HUD,
            ConsoleModule,
            KillAura,
            ChestESP,
            NoRender,
            ESP,
            FastPlace,
            SignColors,
            AntiWeather,
            Xray,
            OppDetector,
            AutoMine,
            SafeWalk,
            AutoTool,
            AutoBuild,
            AsyncScreenshot,
            Sprint,
        // hud modules
            Watermark,
            Position
    )

    /* Sorted by length of module name */
    val modsSorted = mods.copyOf()

    private val keyListener = Listener<KeyPressEvent> { (it as KeyPressEvent)
        mods.forEach { mod -> if (mod.bind == it.key && it.key != 0) mod.toggle() }
    }

    fun getCategoryMods(category: ModCategory): List<Module> {
        return mods.filter { it.category == category }.toList()
    }

    init {
        EventBus.addListener(keyListener, KeyPressEvent::class)

        /* Sort module lists for proper display */
        mods.sortBy { it.name }

        val font = Minecraft.getMinecraft().fontRenderer
        // Using the negative value so that our array is from longest to shortest
        modsSorted.sortBy { -font.getStringWidth(it.name) }

        // Add modules to save/load configs
        mods.forEach { ConfigManager.addConfig(it) }
    }
}