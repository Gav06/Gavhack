package dev.gavhack.module.mods;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.gavhack.Gavhack;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudModule extends Module {

    public HudModule() {
        super("Hud", "Game overlay", Category.CLIENT);
        enable();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        final FontRenderer fr = mc.fontRenderer;
        fr.drawString(ChatFormatting.GREEN + Gavhack.MOD_NAME + " " + Gavhack.VERSION, 2f, 2f, -1, false);

        int y = fr.FONT_HEIGHT + 3;
        for (Module module : gavhack.getModuleManager().getModules()) {
            if (module.isEnabled()) {
                fr.drawString(ChatFormatting.GREEN + ">" + module.getName(), 2f, y, -1, false);
                y += fr.FONT_HEIGHT + 2;
            }
        }
    }
}
