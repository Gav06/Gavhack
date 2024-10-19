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

            // Remove unnecessary set (we know our hashSet won't be null)
            if (listenerMap[clazz]!!.isEmpty())
                listenerMap.remove(clazz)
        }
    }


//    private fun getListenerFields(obj: Any): Set<Listener<Event>> {
//        val listeners = HashSet<Listener<Event>>()
//
//        for (field in obj.javaClass.declaredFields) {
//            if (!field.isAccessible)
//                field.isAccessible = true
//
//            if (field.type == Listener::class.java && field.isAnnotationPresent(Register::class.java)) {
//                try {
//                    listeners.add(field.get(obj) as Listener<Event>)
//                } catch (e: Exception) {
//                    // IllegalAccessException or IllegalArgumentException (shouldn't ever happen in theory)
//                    e.printStackTrace()
//                    continue
//                }
//            }
//        }
//
//        return listeners
//    }
}