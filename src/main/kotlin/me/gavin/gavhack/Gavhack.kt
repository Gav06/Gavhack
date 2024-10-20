package me.gavin.gavhack

import me.gavin.gavhack.command.CommandManager
import me.gavin.gavhack.config.ConfigManager
import me.gavin.gavhack.macro.MacroManager
import me.gavin.gavhack.module.ModuleManager
import me.gavin.gavhack.ui.ClickGUI
import me.gavin.gavhack.ui.OverlayUI
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color

object Gavhack {

    const val NAME = "gavhack"
    const val VERSION_MAJOR = 2
    const val VERSION_MINOR = 0
    const val VERSION_STRING = "$VERSION_MAJOR.$VERSION_MINOR"

    val log : Logger = LogManager.getFormatterLogger(NAME)

    var colorScheme : Color = Color(0x55FF55, false)

    fun init() {
        val startTime = System.currentTimeMillis()
        log.info("Beginning $NAME v$VERSION_STRING initialization")

        // Disable JNDI lookups (no more Log4Shell)
        System.setProperty("log4j2.formatMsgNoLookups", "true")
        log.info("JNDI lookups disabled")

        ModuleManager
        log.info("Modules initialized")

        CommandManager
        log.info("Commands initialized")

        MacroManager
        log.info("Macros initialized")

        OverlayUI
        log.info("UI renderer initialized")

        ClickGUI
        log.info("Click GUI initialized")

        ConfigManager
        log.info("Config initialized")

        log.info("Completed $NAME initialization in ${System.currentTimeMillis() - startTime}ms")
    }

}