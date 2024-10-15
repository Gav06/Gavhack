package me.gavin.gavhack

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.TestEvent
import me.gavin.gavhack.module.ModuleManager
import me.gavin.gavhack.ui.OverlayUI
import net.minecraft.util.EnumChatFormatting
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color

object Gavhack {

    const val NAME = "Gavhack"
    const val VER_MAJOR = 2
    const val VER_MINOR = 0
    const val VER_STR = "$VER_MAJOR.$VER_MINOR"

    val log : Logger = LogManager.getFormatterLogger(NAME)

    var colorScheme : Color = Color(0x55FF55, false)

    fun init() {
        val startTime = System.currentTimeMillis()
        log.info("Beginning $NAME v$VER_STR initialization")

        // Disable JNDI lookups (no more Log4Shell)
        System.setProperty("log4j2.formatMsgNoLookups", "true")
        log.info("JNDI lookups disabled")

        ModuleManager
        log.info("Modules initialized")

        OverlayUI
        log.info("UI renderer initialized")

        log.info("Completed $NAME initialization in ${System.currentTimeMillis() - startTime}ms")
    }

}