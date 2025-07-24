package me.gavin.gavhack.event

import kotlin.reflect.KClass

/**
 * Super simple Event bus I like to use.
 * Runs off of HashMaps & functional interfaces
 */
object EventBus {

    private val listenerMap = HashMap<KClass<out Event>, HashSet<Listener<Event>>>()

    fun addListener(listener: Listener<Event>, clazz: KClass<out Event>) {
        // If we don't have a set already, let's create one
        if (!listenerMap.contains(clazz))
            listenerMap[clazz] = HashSet()

        // Add our listener to set
        listenerMap[clazz]?.add(listener)
    }

    // returns true if event is cancelled
    fun <T : Event> post(event: T): Boolean {
        if (listenerMap.contains(event::class)) {
            listenerMap[event::class]?.forEach {
                it.invoke(event)
            }
        }

        return event.cancelled
    }

    fun removeListener(listener: Listener<Event>, clazz: KClass<out Event>) {
        if (listenerMap.contains(clazz)) {
            listenerMap[clazz]?.remove(listener)

            // Remove an unnecessary set (we know our hashSet won't be null)
            if (listenerMap[clazz]!!.isEmpty())
                listenerMap.remove(clazz)
        }
    }
}