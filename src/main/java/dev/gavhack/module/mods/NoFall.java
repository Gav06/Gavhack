package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.mixin.accessors.ICPacketPlayer;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoFall extends Module {

    private final EnumSetting<NoFallMode> mode = new EnumSetting<>("Mode", NoFallMode.Packet);

    public NoFall() {
        super("NoFall", "Tries to prevent fall damage", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && mc.player.fallDistance > 3.0f) {
            final CPacketPlayer packet = (CPacketPlayer) event.getPacket();

            if (mode.getValue() == NoFallMode.Packet) {
                ((ICPacketPlayer) packet).CPacketPlayer_onGround(true);
            }
        }
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (mode.getValue() == NoFallMode.Tick && mc.player.fallDistance > 3.0f) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }
    }

    public enum NoFallMode {
//        NCP,
        Packet,
        Tick
    }
}
