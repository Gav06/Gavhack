package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PacketReadEvent
import me.gavin.gavhack.event.events.PacketSendEvent
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.event.events.WorldUnloadEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import net.minecraft.util.Vec3

object Freecam: Module("Freecam", "Look around freely client-side", ModCategory.WORLD) {

    private var startPos = Vec3.createVectorHelper(0.0, 0.0, 0.0)
    private var yaw = 0.0f
    private var pitch = 0.0f

    override fun onEnable() {
        if (mc.theWorld == null || mc.thePlayer == null) {
            disable()
        } else {
            startFreecam()
        }
    }

    override fun onDisable() {
        if (mc.thePlayer != null && mc.theWorld != null) {
            stopFreecam()
        }
    }

    private fun startFreecam() {
        this.startPos = Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)
        this.yaw = mc.thePlayer.rotationYaw
        this.pitch = mc.thePlayer.rotationPitch
    }

    private fun stopFreecam() {
        mc.thePlayer.setPositionAndRotation(startPos.xCoord, startPos.yCoord, startPos.zCoord, yaw, pitch)
    }

    private val tickListener = Listener<PlayerLivingTickEvent> {

    }

    private val packetListener = Listener<PacketReadEvent> { it as PacketSendEvent
           if (it.packet is C04PacketPlayerPosition) {
               it.cancelled = true
           }
    }

    private val worldListener = Listener<WorldUnloadEvent> {
        disable()
    }

    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)
        addEventListener(packetListener, PacketSendEvent::class)
        addEventListener(worldListener, WorldUnloadEvent::class)
    }
}