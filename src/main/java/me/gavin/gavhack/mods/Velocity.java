package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.FloatSetting;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {

    private final FloatSetting horizontal = new FloatSetting("Horizontal", 0.0f, 0.0f, 100.0f);
    private final FloatSetting vertical = new FloatSetting("Vertical", 0.0f, 0.0f, 100.0f);

    public Velocity() {
        super("Velocity", "Removes velocity", Category.Movement);
        this.settings.add(horizontal);
        this.settings.add(vertical);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            final SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();
            if (packet.entityID == mc.player.getEntityId()) {
                if (horizontal.getValue() == 0 && vertical.getValue() == 0) {
                    event.setCanceled(true);
                } else {
                    packet.motionX *= (horizontal.getValue() / 100f);
                    packet.motionY *= (vertical.getValue() / 100f);
                    packet.motionZ *= (horizontal.getValue() / 100f);
                }
            }
        } else if (event.getPacket() instanceof SPacketExplosion) {
            final SPacketExplosion packet = (SPacketExplosion) event.getPacket();

            packet.motionX *= (horizontal.getValue() / 100f);
            packet.motionY *= (vertical.getValue() / 100f);
            packet.motionZ *= (horizontal.getValue() / 100f);
        } else if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 31 && packet.getEntity(mc.world) instanceof EntityFishHook) {
                final EntityFishHook hook = (EntityFishHook) packet.getEntity(mc.world);
                if (hook.caughtEntity == mc.player)
                    event.setCanceled(true);
            }
        }
    }
}
