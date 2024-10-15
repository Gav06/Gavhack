package me.gavin.gavhack.event

fun interface Listener<out T : Event> {

    fun invoke(t: Event)
}