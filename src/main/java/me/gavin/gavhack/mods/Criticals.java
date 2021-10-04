package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.misc.MilliTimer;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.ModeSetting;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals extends Module {

    private final MilliTimer timer = new MilliTimer();

    private final ModeSetting critMode = new ModeSetting("Mode", "Simple", "Simple", "Weird", "Bypass", "Jump");

    public Criticals() {
        super("Criticals", "Makes you hit more criticals", Category.Combat);
        this.settings.add(critMode);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (shouldRun()) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                final CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
                if (packet.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    if (critMode.getMode().equals("Simple")) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    } else if (critMode.getMode().equals("Weird")) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.11, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.11, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1100013579, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    } else if (critMode.getMode().equals("Bypass")) {
                        if (this.mc.player.fallDistance > 0.0f) {
                            return;
                        }
                        if (this.mc.player.isInLava() || this.mc.player.isInWater()) {
                            return;
                        }
                        if (this.timer.passed(1000)) {
                            this.timer.reset();
                            this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.11, this.mc.player.posZ, false));
                            this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.1100013579, this.mc.player.posZ, false));
                            this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.3579E-6, this.mc.player.posZ, false));
                        }
                    } else {
                        mc.player.jump();
                    }
                }
            }
        }
    }
}