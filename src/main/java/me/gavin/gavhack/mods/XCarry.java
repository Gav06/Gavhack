package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.mods.api.Module;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class XCarry extends Module {
    public XCarry() {
        super("XCarry", "Carry items in your crafting slots", Category.Misc);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            final CPacketCloseWindow packet = (CPacketCloseWindow) event.getPacket();

            if (packet.windowId == mc.player.inventoryContainer.windowId) {
                event.setCanceled(true);
            }
        }
    }
}
