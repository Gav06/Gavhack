package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.EnumSetting
import net.minecraft.item.ItemBow
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.util.EnumFacing

object BowSpam: Module("BowSpam", "Spam arrows quickly", ModCategory.COMBAT) {

    private val mode = addSetting(EnumSetting("Mode", Mode.NORMAL))

    private val updateListener = Listener<PlayerLivingTickEvent> {
        val currentItem = mc.thePlayer.heldItem
        if (currentItem == null || currentItem.item !is ItemBow) return@Listener

        if (!mc.thePlayer.isUsingItem) return@Listener

        if (mode.enumValue() == Mode.INSTANT) {
                mc.netHandler.addToSendQueue(
                    C08PacketPlayerBlockPlacement(
                        0, 0, 0,
                        255,
                        currentItem,
                        0f,
                        0f,
                        0f
                    )
                )

                for (i in 0..19) {
                    mc.netHandler.addToSendQueue(
                        C05PacketPlayerLook(
                            mc.thePlayer.rotationYaw,
                            mc.thePlayer.rotationPitch,
                            true
                        )
                    )
                }

                mc.netHandler.addToSendQueue(
                    C07PacketPlayerDigging(
                        5,
                        0, 0, 0,
                        EnumFacing.DOWN.ordinal
                    )
                )
                mc.thePlayer.itemInUseCount = currentItem.maxItemUseDuration - 1
        } else {
            if (mc.thePlayer.itemInUseCount > 3) {

                mc.netHandler.addToSendQueue(C07PacketPlayerDigging(5, 0, 0, 0, 0));

                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, currentItem)
                mc.thePlayer.stopUsingItem()
            }
        }
    }

    init {
        addEventListener(updateListener, PlayerLivingTickEvent::class)
    }

    enum class Mode {
        INSTANT,
        NORMAL
    }
}