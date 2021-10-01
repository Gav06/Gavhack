package me.gavin.gavhack.mods;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.BooleanSetting;
import me.gavin.gavhack.settings.FloatSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    private final FloatSetting range = new FloatSetting("Range", 4.5f, 1.0f, 8.0f);
    private final FloatSetting wallsRange = new FloatSetting("Walls range", 3.0f, 0.0f, 5.0f);
    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting animals = new BooleanSetting("Animals", true);
    private final BooleanSetting mobs = new BooleanSetting("Mobs", true);
    private final BooleanSetting others = new BooleanSetting("Others", true);

    public KillAura() {
        super("KillAura", "Attack entities", Category.Combat);
        this.setBind(Keyboard.KEY_R);
        this.settings.add(range);
        this.settings.add(wallsRange);
        this.settings.add(players);
        this.settings.add(animals);
        this.settings.add(mobs);
        this.settings.add(others);
    }

    private EntityLivingBase target;

    @EventTarget
    public void onUpdate(PlayerLivingUpdateEvent event) {
        if (this.target == null) {
            final List<EntityLivingBase> list = mc.world.loadedEntityList.stream()
                    .filter(e -> e instanceof EntityLivingBase)
                    .filter(e -> !e.equals(mc.player))
                    .map(e -> (EntityLivingBase)e)
                    .filter(this::shouldAttack)
                    .sorted(Comparator.comparing(e -> e.getDistance(mc.player)))
                    .collect(Collectors.toList());

            for (EntityLivingBase livingBase : list) {
                if (targetCheck(livingBase)) {
                    target = livingBase;
                    return;
                }
            }
        } else {
            if (targetCheck(target)) {
                if (mc.player.getCooledAttackStrength(0f) >= 1.0f) {
                    mc.playerController.attackEntity(mc.player, target);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            } else {
                target = null;
            }
        }
    }

    private boolean shouldAttack(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return this.players.isEnabled();
        } else if (entity instanceof EntityAnimal) {
            return this.animals.isEnabled();
        } else if (entity instanceof EntityMob) {
            return this.mobs.isEnabled();
        } else {
            return this.others.isEnabled();
        }
    }

    private boolean targetCheck(EntityLivingBase entity) {
        float rangeToUse;
        if (this.mc.player.canEntityBeSeen(entity)) {
            rangeToUse = this.range.getValue();
        } else {
            rangeToUse = this.wallsRange.getValue();
        }

        return this.mc.player.getDistance(entity) <= rangeToUse && entity.isEntityAlive() && entity.getHealth() > 0.0;
    }
}
