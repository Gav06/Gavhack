package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.mixin.accessors.IMinecraft;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", "place fast", Category.PLAYER);
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        ((IMinecraft) mc).Minecraft_rightClickDelayTimer(0);
    }
}
