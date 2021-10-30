package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BoatBypass extends Module {

    public BoatBypass() {
        super("BoatBypass", "bypass ncp's restrictions for boat placements", Category.OTHER);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock) event.getPacket();
            if (mc.player.getHeldItem(packet.getHand()).getItem() == Items.BOAT) {
                event.setCanceled(true);
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(packet.getHand()));
            }
        }
    }
}
