package dev.gavhack.mixin.accessors;

import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SPacketExplosion.class)
public interface ISPacketExplosion {

    @Accessor("motionX")
    void SPacketExplosion_motionX_gavhack(float motionX);

    @Accessor("motionY")
    void SPacketExplosion_motionY_gavhack(float motionY);

    @Accessor("motionZ")
    void SPacketExplosion_motionZ_gavhack(float motionZ);
}
