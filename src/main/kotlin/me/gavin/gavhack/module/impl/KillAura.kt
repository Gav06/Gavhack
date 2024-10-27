package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import kotlin.jvm.optionals.getOrNull

object KillAura: Module("KillAura", "Attack entities automatically", ModCategory.COMBAT) {

    private var target: EntityLiving? = null
    private var lastAttack = 0L

    private val tickListener = Listener<PlayerLivingTickEvent> {
        if (!isEnabled())
            return@Listener

        if (target == null) {
            target = findTargetEntity()
        } else {
            if (!entityCheck(target!!)) {
                target = null
                return@Listener
            }

            if (System.currentTimeMillis() - lastAttack >= 100L) {
                lastAttack = System.currentTimeMillis()
                mc.playerController.attackEntity(mc.thePlayer, target)
                mc.thePlayer.swingItem()
            }
        }
    }

    private fun findTargetEntity(): EntityLiving? {
        return mc.theWorld.loadedEntityList.stream()
            .filter { it is EntityLiving }
            .map { it as EntityLiving }
            .filter { entityCheck(it) }
            .findFirst()
            .getOrNull()
    }

    fun entityCheck(e: EntityLiving): Boolean {
        return e.isEntityAlive && e.getDistanceToEntity(mc.thePlayer) < 5f && !e.isDead && e.health > 0
    }

    init {
        EventBus.addListener(tickListener, PlayerLivingTickEvent::class)
    }
}