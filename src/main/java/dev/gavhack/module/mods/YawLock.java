package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class YawLock extends Module {

    private final FloatSetting slice = new FloatSetting("Slice", 4f, 1f, 90f);

    public YawLock() {
        super("YawLock", "Locks your yaw", Category.PLAYER);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        int angle = 360 / (int) slice.getValue();
        float yaw = mc.player.rotationYaw;
        yaw = Math.round(yaw / angle) * angle;
        mc.player.rotationYaw = yaw;
        if (mc.player.isRiding())
            mc.player.getRidingEntity().rotationYaw = yaw;
    }
}
