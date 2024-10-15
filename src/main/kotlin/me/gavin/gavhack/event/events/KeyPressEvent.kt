package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event

/* Called when a key is pressed in-game while the player is not in a GuiScreen */
data class KeyPressEvent(val key : Int, val pressed: Boolean, val keyChar: Char) : Event()
