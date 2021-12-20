package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.mixin.accessors.ICPacketCustomPayload;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class FakeVanilla extends Module {

    public FakeVanilla() {
        super("FakeVanilla", "Tells the server that you are using a vanilla client", Category.OTHER);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof FMLProxyPacket && !mc.isSingleplayer())
            event.setCanceled(true);

        if (event.getPacket() instanceof CPacketCustomPayload) {
            final CPacketCustomPayload packet = (CPacketCustomPayload) event.getPacket();
            if (packet.getChannelName().equals("MC|Brand")) {
                ((ICPacketCustomPayload)packet).CPacketCustomPayload_data(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
            }
        }
    }
}
