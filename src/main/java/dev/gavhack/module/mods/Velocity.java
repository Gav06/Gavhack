package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.mixin.accessors.ISPacketExplosion;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "disable velocity", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        if (mc.player == null)
            return;

        if (event.getPacket() instanceof SPacketExplosion) {
            final SPacketExplosion packet = (SPacketExplosion) event.getPacket();
            final ISPacketExplosion accessor = (ISPacketExplosion) packet;
            accessor.SPacketExplosion_motionX_gavhack(0f);
            accessor.SPacketExplosion_motionY_gavhack(0f);
            accessor.SPacketExplosion_motionZ_gavhack(0f);
        } else if (event.getPacket() instanceof SPacketEntityVelocity) {
            final SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();
            if (packet.getEntityID() == mc.player.getEntityId()) {
                event.setCanceled(true);
            }
        } else if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 31 && packet.getEntity(mc.world) instanceof EntityFishHook) {
                final EntityFishHook hook = (EntityFishHook) packet.getEntity(mc.world);
                if (hook.caughtEntity == mc.player) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
