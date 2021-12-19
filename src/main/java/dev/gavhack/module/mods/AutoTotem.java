package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTotem extends Module {

    private final EnumSetting<Mode> mode = new EnumSetting<>("Mode", Mode.Always);
    private final FloatSetting health = new FloatSetting("Health", 10f, 0.1f, 36f);

    public AutoTotem() {
        super("AutoTotem", "you already know", Category.COMBAT);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
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

    private enum Mode {
        Always,
        Health
    }
}
