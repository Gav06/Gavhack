package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSwing extends Module {

    public static final EnumSetting<SwingMode> swingMode = new EnumSetting<>("Mode", SwingMode.All);

    public NoSwing() {
        super("NoSwing", "Cancels the swing animation", Category.PLAYER);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (swingMode.getValue() == SwingMode.Packet && event.getPacket() instanceof CPacketAnimation) {
            event.setCanceled(true);
        }
    }

    public enum SwingMode {
        All,
        Packet
    }
}
