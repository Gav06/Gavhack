package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.ParticleSpawnEvent;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.BooleanSetting;
import net.minecraft.util.EnumParticleTypes;
import static net.minecraft.util.EnumParticleTypes.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoParticles extends Module {

    private final BooleanSetting explosion = new BooleanSetting("Explosions", true);

    public NoParticles() {
        super("NoParticles", "Stop certain particles from spawning", Category.Render);
        this.settings.add(explosion);
    }

    @SubscribeEvent
    public void onParticleSpawn(ParticleSpawnEvent e) {
        if (explosion.isEnabled() && (e.getType() == EXPLOSION_NORMAL || e.getType() == EXPLOSION_HUGE || e.getType() == EXPLOSION_LARGE)) {
            e.setCanceled(true);
            return;
        }


    }
}
