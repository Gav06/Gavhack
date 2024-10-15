package me.gavin.gavhack.event

import kotlin.reflect.KClass

object EventBus {

    private val listenerMap = HashMap<KClass<out Event>, HashSet<Listener<Event>>>()

    fun addListener(listener: Listener<Event>, clazz: KClass<out Event>) {
        // If we don't have a set already, let's create one
        if (!listenerMap.contains(clazz))
            listenerMap[clazz] = HashSet()

        // Add our listener to set
        listenerMap[clazz]?.add(listener)
    }

    fun <T : Event> post(event: T) {
        if (listenerMap.contains(event::class)) {
            listenerMap[event::class]?.forEach {
                it.invoke(event)
            }
        }
    }

    fun removeListener(listener: Listener<Event>, clazz: KClass<out Event>) {
        if (listenerMap.contains(clazz)) {
            listenerMap[clazz]?.remove(listener)

            // Remove unnecessary set (we know our hashSet won't be null)
            if (listenerMap[clazz]!!.isEmpty())
                listenerMap.remove(clazz)
        }
    }
}