package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitySpeed extends Module {

    private final FloatSetting speed = new FloatSetting("Speed", 1.0f, 0.1f, 5.0f);

    public EntitySpeed() {
        super("EntitySpeed", "fast pig", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (mc.player.isRiding() && (mc.player.getRidingEntity() instanceof AbstractHorse || mc.player.getRidingEntity() instanceof EntityPig)) {
            final Entity entity = mc.player.getRidingEntity();
            entity.rotationYaw = mc.player.rotationYaw;
            double[] speed = MathUtil.directionSpeed(this.speed.getValue(), mc.player, mc.getRenderPartialTicks());
            entity.motionX = speed[0];
            entity.motionZ = speed[1];
        }
    }
}
