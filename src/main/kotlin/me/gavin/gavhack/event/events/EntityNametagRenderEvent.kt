package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase

class EntityNametagRenderEvent(
    val entity: EntityLivingBase,
    val text: String,
    val x: Double,
    val y: Double,
    val z: Double
): Event()