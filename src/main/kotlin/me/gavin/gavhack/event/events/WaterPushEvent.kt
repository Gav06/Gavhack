package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.entity.Entity

data class WaterPushEvent(val entity: Entity): Event()