package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoRespawn extends Module {

    private final BooleanSetting deathCoords = new BooleanSetting("DeathCoords", false);

    public AutoRespawn() {
        super("AutoRespawn", "Respawn automatically", Category.COMBAT);

    }

    @SubscribeEvent
    public void onGuiOpened(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            mc.player.respawnPlayer();
        }
    }
}
