package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.*
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.BooleanSetting
import me.gavin.gavhack.module.setting.FloatSetting
import net.minecraft.entity.Entity
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.client.C0CPacketInput
import net.minecraft.util.Vec3
import org.lwjgl.input.Keyboard

object Freecam: Module("Freecam", "Look around freely client-side", ModCategory.WORLD) {

    private val cancelPackets = addSetting(BooleanSetting("CancelPackets", true))
    private val speed = addSetting(FloatSetting("Speed", 20.0f, 5.0f, 100.0f))

    private var startPos = Vec3.createVectorHelper(0.0, 0.0, 0.0)
    private var yaw = 0.0f
    private var pitch = 0.0f

    private var allowFly = false
    private var isFlying = false
    private var ridingEntity: Entity? = null

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
        this.allowFly = mc.thePlayer.capabilities.allowFlying
        this.isFlying = mc.thePlayer.capabilities.isFlying

        if (mc.thePlayer.ridingEntity != null) {
            this.ridingEntity = mc.thePlayer.ridingEntity
            mc.thePlayer.mountEntity(null)
        }
    }

    private fun stopFreecam() {
        mc.thePlayer.setPositionAndRotation(startPos.xCoord, startPos.yCoord, startPos.zCoord, yaw, pitch)
        mc.thePlayer.isSprinting = false
        mc.thePlayer.capabilities.isFlying = isFlying
        mc.thePlayer.capabilities.allowFlying = allowFly
        mc.thePlayer.noClip = false

        mc.thePlayer.motionX = 0.0
        mc.thePlayer.motionY = 0.0
        mc.thePlayer.motionZ = 0.0

        if (ridingEntity != null && ridingEntity!!.isEntityAlive) {
            mc.thePlayer.mountEntity(ridingEntity)
            ridingEntity = null
        }
    }

    private val tickListener = Listener<PlayerLivingTickEvent> {
        mc.thePlayer.capabilities.allowFlying = true
        mc.thePlayer.capabilities.isFlying = true
        mc.thePlayer.noClip = true
        mc.thePlayer.onGround = false
        mc.thePlayer.isSprinting = false
        mc.thePlayer.capabilities.flySpeed = speed.getValue() / 100.0f

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.keyCode)) {
            mc.thePlayer.motionY += speed.getValue() / 50.0f
        }
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode)) {
            mc.thePlayer.motionY -= speed.getValue() / 50.0f
        }
    }

    private val packetListener = Listener<PacketReadEvent> { it as PacketSendEvent
        if (!cancelPackets.value) return@Listener

        if (it.packet is C03PacketPlayer || it.packet is C0CPacketInput) {
               it.cancelled = true
            if (it.packet is C0CPacketInput) { it.packet.sneak = false }
           }
    }

    private val worldListener = Listener<WorldUnloadEvent> {
        disable()
    }

    private val handListener = Listener<RenderHandEvent> {
        it.cancelled = true
    }

    private val bobListener = Listener<ViewBobbingEvent> {
        it.cancelled = true
    }

    private val fovListener = Listener<FieldOfViewEvent> { it as FieldOfViewEvent
        it.fovModifier = 1.0f
    }

    private val entityPushListener = Listener<EntityCollisionEvent> { it as EntityCollisionEvent
        if (it.entity == mc.thePlayer) {
            it.cancelled = true
        }
    }

    private val waterPushListener = Listener<WaterPushEvent> { it as WaterPushEvent
        if (it.entity == mc.thePlayer) {
            it.cancelled = true
        }
    }

    private val blockPushListener = Listener<PlayerPushOutOfBlocksEvent> {
        it.cancelled = true
    }


    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)
        addEventListener(packetListener, PacketSendEvent::class)
        addEventListener(worldListener, WorldUnloadEvent::class)
        addEventListener(handListener, RenderHandEvent::class)
        addEventListener(bobListener, ViewBobbingEvent::class)
        addEventListener(fovListener, FieldOfViewEvent::class)
        addEventListener(entityPushListener, EntityCollisionEvent::class)
        addEventListener(waterPushListener, WaterPushEvent::class)
        addEventListener(blockPushListener, PlayerPushOutOfBlocksEvent::class)
    }
}