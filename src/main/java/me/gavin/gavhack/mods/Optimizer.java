package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Optimizer extends Module {
    public Optimizer() {
        super("Optimizer", "Boosts FPS", Category.Misc);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        try {
            // most high tech optimization methods at gavhack industries
            Thread.sleep(75L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
