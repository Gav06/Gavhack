package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Sneak extends Module {

    public Sneak() {
        super("Sneak", "Crouches server-side", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
//        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
    }
}
