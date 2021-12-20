package dev.gavhack.module.mods;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.gavhack.Gavhack;
import dev.gavhack.events.PacketEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudModule extends Module {

    private long lastPacketTime = 0L;

    public HudModule() {
        super("Hud", "Game overlay", Category.CLIENT);
        enable();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        final FontRenderer fr = mc.fontRenderer;
        fr.drawStringWithShadow(ChatFormatting.GREEN + Gavhack.MOD_NAME + " " + Gavhack.VERSION, 2f, 2f, -1);

        int y = fr.FONT_HEIGHT + 3;
        for (Module module : gavhack.getModuleManager().getModules()) {
            if (module.isEnabled()) {
                fr.drawStringWithShadow(ChatFormatting.GREEN + ">" + module.getName() + " " + module.getExtraInfo(), 2f, y, -1);
                y += fr.FONT_HEIGHT + 1;
            }
        }

        // lag detector
        if (System.currentTimeMillis() - lastPacketTime > 2000L && !mc.isSingleplayer()) {
            final String s = String.format("Server not responding: %.1fs", ((System.currentTimeMillis() - lastPacketTime) / 1000.0));
            fr.drawStringWithShadow(s, (event.getResolution().getScaledWidth() / 2f) - (fr.getStringWidth(s) / 2f), 12, 0x00ff00);
        }
    }

    @SubscribeEvent
    public void onReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            lastPacketTime = System.currentTimeMillis();
        }
    }
}
