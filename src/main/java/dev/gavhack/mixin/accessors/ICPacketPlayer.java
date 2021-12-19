package dev.gavhack.mixin.accessors;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CPacketPlayer.class)
public interface ICPacketPlayer {

    @Accessor("onGround")
    void CPacketPlayer_onGround(boolean onGround);

    @Accessor("x")
    void CPacketPlayer_x(double x);

    @Accessor("y")
    void CPacketPlayer_y(double y);

    @Accessor("z")
    void CPacketPlayer_z(double z);
}
