package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IceSpeed extends Module {

    private final FloatSetting speed = new FloatSetting("Slipperiness", .4f, .2f, 1f);

    public IceSpeed() {
        super("IceSpeed", "Change speed over ice", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        Blocks.ICE.slipperiness = speed.getValue();
        Blocks.PACKED_ICE.slipperiness = speed.getValue();
        Blocks.FROSTED_ICE.slipperiness = speed.getValue();
    }

    @Override
    protected void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}
