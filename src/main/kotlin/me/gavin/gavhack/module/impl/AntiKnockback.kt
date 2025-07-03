package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PacketReadEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.minecraft.network.play.server.S27PacketExplosion

object AntiKnockback: Module("AntiKnockback", "Removes knockback", ModCategory.MOVEMENT) {

    private val onPacketRead = Listener<PacketReadEvent> { (it as PacketReadEvent)
        when (it.packet) {
            is S12PacketEntityVelocity -> {
                if (it.packet.entityId == mc.thePlayer.entityId) it.cancelled = true
            }
            is S27PacketExplosion -> {
                it.packet.motionX = 0.0f
                it.packet.motionY = 0.0f
                it.packet.motionZ = 0.0f
            }
        }
    }

    init {
        EventBus.addListener(onPacketRead, PacketReadEvent::class)
    }
}