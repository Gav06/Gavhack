package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.mods.api.Module;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PortalGodMode extends Module {

    public PortalGodMode() {
        super("PortalGodMode", "Cancels teleport packets", Category.World);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketConfirmTeleport) {
            event.setCanceled(true);
        }
    }
}
