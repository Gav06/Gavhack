package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event

data class ChatMessageEvent(var message: String) : Event()