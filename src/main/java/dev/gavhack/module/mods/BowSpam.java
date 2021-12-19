package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BowSpam extends Module {

    public BowSpam() {
        super("BowSpam", "Spam arrows", Category.COMBAT);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
            if (mc.player.isHandActive()) {
                if (mc.player.getItemInUseMaxCount() > 3) {

                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));

                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                    mc.player.stopActiveHand();
                }
            }
        }
    }
}
