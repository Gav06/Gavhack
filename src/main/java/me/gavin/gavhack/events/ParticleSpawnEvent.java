package me.gavin.gavhack.events;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ParticleSpawnEvent extends Event {

    private final EnumParticleTypes particleType;

    public ParticleSpawnEvent(int id) {
        this.particleType = EnumParticleTypes.getParticleFromId(id);
    }

    public EnumParticleTypes getType() {
        return particleType;
    }
}
