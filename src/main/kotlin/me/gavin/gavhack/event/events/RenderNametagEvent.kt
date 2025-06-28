package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.entity.Entity

data class RenderNametagEvent(val entity: Entity, val text: String): Event() {
}