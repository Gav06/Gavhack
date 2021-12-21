package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PortalTweaks extends Module {

    public static BooleanSetting hitbox;
    public static BooleanSetting chat;

    private final BooleanSetting godmode = new BooleanSetting("GodMode", false);

    public PortalTweaks() {
        super("PortalTweaks", "modify portal behavior", Category.PLAYER);
        hitbox = new BooleanSetting("Hitbox", true);
        chat = new BooleanSetting("Gui's", true);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketConfirmTeleport && godmode.isEnabled()) {
            event.setCanceled(true);
        }
    }
}
