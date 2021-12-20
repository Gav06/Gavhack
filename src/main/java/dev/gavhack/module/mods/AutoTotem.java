package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.stream.IntStream;

public class AutoTotem extends Module {

    private final EnumSetting<Mode> mode = new EnumSetting<>("Mode", Mode.Always);
    private final FloatSetting health = new FloatSetting("Health", 10f, 0.1f, 36f);

    public AutoTotem() {
        super("AutoTotem", "you already know", Category.COMBAT);
    }

    // Using clienttickevent because it fires before playerupdateevent
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        if (mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.TOTEM_OF_UNDYING)
            return;

        int totemSlot = -1;
        for (int i = 0; i < 36; i++) {
            Item slotItem = mc.player.inventory.getStackInSlot(i).getItem();
            if (slotItem == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    i += 36;
                }
                totemSlot = i;
                break;
            }
        }

        if (mode.getValue() == Mode.Health && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) > health.getValue())
            return;

        if (totemSlot != -1) {
            // 45 is the offhand slot
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, totemSlot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, totemSlot, 0, ClickType.PICKUP, mc.player);
        }
    }

    private int getTotalTotemAmount() {
        int count = mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING ? mc.player.getHeldItemOffhand().getCount() : 0;
        for (int i = 9; i < 45; i++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                count += stack.getCount();
            }
        }

        return count;
    }

    @Override
    public String getExtraInfo() {
        return "[" + getTotalTotemAmount() + "]";
    }

    private enum Mode {
        Always,
        Health
    }
}
