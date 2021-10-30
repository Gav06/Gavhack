package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;

public class KillAura extends Module {

    private final FloatSetting range = new FloatSetting("Range", 4.0f, 2.0f, 5.0f);

    public KillAura() {
        super("KillAura", "craphack", Category.COMBAT);
    }

    private EntityLivingBase targetEntity = null;

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        if (targetEntity != null) {
            if (!targetCheck(targetEntity)) {
                targetEntity = null;
                return;
            } else {
                if (mc.player.getCooledAttackStrength(0f) >= 1f) {
                    
                }
            }
        } else {
            targetEntity = findTarget();
        }
    }

    private boolean targetCheck(EntityLivingBase ent) {
        return ent.getDistance(mc.player) <= range.getValue() && ent.isEntityAlive() && ent.getHealth() > 0;
    }

    private EntityLivingBase findTarget() {
        return mc.world.loadedEntityList.stream()
                .filter(e -> !e.equals(mc.player))
                .filter(e -> e instanceof EntityLivingBase)
                .map(e -> (EntityLivingBase) e)
                .filter(this::targetCheck)
                .sorted(Comparator.comparing(e -> e.getDistance(mc.player)))
                .findFirst().orElse(null);
    }
}
