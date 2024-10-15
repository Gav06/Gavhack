package me.gavin.gavhack

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.TestEvent
import me.gavin.gavhack.ui.OverlayUI
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Gavhack {

    const val NAME = "Gavhack"
    const val VER_MAJOR = 2
    const val VER_MINOR = 0
    const val VER_STR = "$VER_MAJOR.$VER_MINOR"

    private val listener = Listener<TestEvent> { (it as TestEvent)
        println(it.message)
    }

    val log : Logger = LogManager.getFormatterLogger(NAME)

    fun init() {
        log.info("Beginning $NAME v$VER_STR initialization")

        // Disable JNDI lookups (no more Log4Shell)
        System.setProperty("log4j2.formatMsgNoLookups", "true")

        // init ui
        OverlayUI


    }

}