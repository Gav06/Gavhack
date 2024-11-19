package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.WorldLoadEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module

object Fullbright: Module("Fullbright", "Max brightness", ModCategory.RENDER) {

    private val lightmapCache = FloatArray(16)

    private val worldLoad = Listener<WorldLoadEvent> { (it as WorldLoadEvent)
        fillLightmap(it.worldClient.provider.lightBrightnessTable)
    }

    override fun onEnable() {
        if (mc.theWorld != null) {
            fillLightmap(mc.theWorld.provider.lightBrightnessTable)
        }
    }

    override fun onDisable() {
        if (mc.theWorld != null) {
            restoreLightmap(mc.theWorld.provider.lightBrightnessTable)
        }
    }

    private fun fillLightmap(arr: FloatArray) {
        for (i in 0..15) {
            lightmapCache[i] = arr[i]
            arr[i] = 1.0f
        }
    }

    private fun restoreLightmap(arr: FloatArray) {
        for (i in 0..15) {
            arr[i] = lightmapCache[i]
        }
    }

    init {
        addEventListener(worldLoad, WorldLoadEvent::class)
    }
}