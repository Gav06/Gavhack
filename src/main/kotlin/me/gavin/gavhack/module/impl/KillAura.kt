package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.NumberSetting
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.player.EntityPlayer
import kotlin.jvm.optionals.getOrNull

object KillAura: Module("KillAura", "Attack entities automatically", ModCategory.COMBAT) {

    private val walls = ToggleSetting("ThruWalls", true)
    private val cpsSetting = NumberSetting("CPS", 10f, 1f, 20f, decimalPlaces = 0)
    private val rangeSetting = NumberSetting("Range", 5.0f, 0.0f, 8.0f)
    private val wallRangeSetting = NumberSetting("WallRange", 3.0f, 0.0f, 5.0f)

    private val players = addSetting(ToggleSetting("Players", true))
    private val hostiles = addSetting(ToggleSetting("Hostiles", true))
    private val passive = addSetting(ToggleSetting("Animals", true))
    private val other = addSetting(ToggleSetting("Others", false))

    private var target: EntityLivingBase? = null
    private var lastAttack = 0L

    private val tickListener = Listener<PlayerLivingTickEvent> {
        if (target == null) {
            target = findTargetEntity()
        } else {
            if (!entityCheck(target!!)) {
                target = null
                return@Listener
            }

            val ms = 1000L / cpsSetting.getValue().toInt()

            if (System.currentTimeMillis() - lastAttack >= ms) {
                lastAttack = System.currentTimeMillis()
                mc.playerController.attackEntity(mc.thePlayer, target)
                mc.thePlayer.swingItem()
            }
        }
    }

    private fun findTargetEntity(): EntityLivingBase? {
        return mc.theWorld.loadedEntityList.stream()
            .filter { it != mc.thePlayer }
            .filter { it is EntityLivingBase }
            .map { it as EntityLivingBase }
            .filter { typeCheck(it) }
            .filter { entityCheck(it) }
            .findFirst()
            .getOrNull()
    }

    private fun entityCheck(e: EntityLivingBase): Boolean {
        return e.isEntityAlive &&
                if (mc.thePlayer.canEntityBeSeen(e))
                    e.getDistanceToEntity(mc.thePlayer) <= rangeSetting.getValue()
                else
                    e.getDistanceToEntity(mc.thePlayer) <= wallRangeSetting.getValue() && walls.value
                && !e.isDead
                && e.health > 0
    }

    private fun typeCheck(e: EntityLivingBase): Boolean {
        return when (e) {
            is EntityPlayer -> players.value
            is EntityAnimal -> passive.value
            is EntityMob -> hostiles.value
            else -> other.value
        }
    }

    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)

        addSetting(walls)
        addSetting(cpsSetting)
        addSetting(rangeSetting)
        addSetting(wallRangeSetting)
    }
}