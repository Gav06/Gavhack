package me.gavin.gavhack.mods;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.mods.api.Module;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class BowShot extends Module {

    public BowShot() {
        super("FastProjectile", "funne exploit", Category.Combat);
        setBind(Keyboard.KEY_B);
    }

    @EventTarget
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerDigging && ((CPacketPlayerDigging) event.getPacket()).getAction().equals(CPacketPlayerDigging.Action.RELEASE_USE_ITEM)) {
            if (mc.player.getHeldItemMainhand().getItem() == Items.BOW && mc.player.getItemInUseMaxCount() >= 20) {
                double sin = -Math.sin(Math.toRadians(mc.player.rotationYaw));
                double cos = Math.cos(Math.toRadians(mc.player.rotationYaw));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + sin * 100, mc.player.posY, mc.player.posZ + cos * 100, true));
            }
        }
    }
}