package dev.gavhack.module.mods;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.gavhack.Gavhack;
import dev.gavhack.events.PacketEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.util.MathUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static org.lwjgl.opengl.GL11.*;

public class HudModule extends Module {

    private long lastPacketTime = 0L;

    private final BooleanSetting watermark = new BooleanSetting("Watermark", true);
    private final BooleanSetting activeMods = new BooleanSetting("ActiveMods", true);
    private final BooleanSetting lagDetector = new BooleanSetting("LagDetector", true);
    private final BooleanSetting radar = new BooleanSetting("PlayerRadar", true);

    public HudModule() {
        super("Hud", "Game overlay", Category.CLIENT);
        enable();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        final FontRenderer fr = mc.fontRenderer;
        if (watermark.isEnabled())
            fr.drawStringWithShadow(ChatFormatting.GREEN + Gavhack.MOD_NAME + " " + Gavhack.VERSION, 2f, 2f, -1);

        if (activeMods.isEnabled()) {
            int y = fr.FONT_HEIGHT + 3;
            if (!watermark.isEnabled())
                y -= 10;
            for (Module module : gavhack.getModuleManager().getModules()) {
                if (module.isEnabled()) {
                    fr.drawStringWithShadow(ChatFormatting.GREEN + ">" + module.getName() + " " + module.getExtraInfo(), 2f, y, -1);
                    y += fr.FONT_HEIGHT + 1;
                }
            }
        }

        // lag detector
        if (lagDetector.isEnabled() && System.currentTimeMillis() - lastPacketTime > 2000L && !mc.isSingleplayer()) {
            final String s = String.format("Server not responding: %.1fs", ((System.currentTimeMillis() - lastPacketTime) / 1000.0));
            fr.drawStringWithShadow(s, (event.getResolution().getScaledWidth() / 2f) - (fr.getStringWidth(s) / 2f), 12, 0x00ff00);
        }

        if (!radar.isEnabled())
            return;

        int edgeBuffer = 2;
        int radarSize = 150;
        int centerX = event.getResolution().getScaledWidth() - (radarSize / 2) - edgeBuffer;
        int centerY = edgeBuffer + (radarSize / 2);

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();

        // Outline
        int left = centerX - (radarSize / 2);
        int top = edgeBuffer;
        int right = event.getResolution().getScaledWidth() - edgeBuffer;
        int bottom = edgeBuffer + radarSize;
        // outline
        glColor3f(0f, 1f, 0f);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        buffer.begin(GL_LINE_LOOP,  DefaultVertexFormats.POSITION);
        buffer.pos(left, top, 0.0).endVertex();
        buffer.pos(right, top, 0.0).endVertex();
        buffer.pos(right, bottom, 0.0).endVertex();
        buffer.pos(left, bottom, 0.0).endVertex();
        tessellator.draw();
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);

        // filled rect
        Gui.drawRect(left, top, right, bottom, 0x90000000);

        glDisable(GL_TEXTURE_2D);
        // horizontal line
        glLineWidth(1f);
        glColor3f(0.5f, 0.5f, 0.5f);
        buffer.begin(GL_LINES, DefaultVertexFormats.POSITION);
        buffer.pos(left, centerY, 0.0).endVertex();
        buffer.pos(right, centerY, 0.0).endVertex();
        tessellator.draw();
        // vertical line
        buffer.begin(GL_LINES, DefaultVertexFormats.POSITION);
        buffer.pos(centerX, top, 0.0).endVertex();
        buffer.pos(centerX, bottom, 0.0).endVertex();
        tessellator.draw();
        // center point
        glPointSize(6f);
        buffer.begin(GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(centerX, centerY, 0.0).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();
        glEnable(GL_TEXTURE_2D);

        // drawing points
        glPointSize(4f);
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.equals(mc.player))
                continue;

            int distX = MathUtil.findDistance1D(mc.player.posX, player.posX);
            int distY = MathUtil.findDistance1D(mc.player.posZ, player.posZ);

            if (distX > radarSize / 2 || distY > radarSize / 2 || distX < -(radarSize / 2) || distY < -(radarSize / 2))
                continue;

            glDisable(GL_TEXTURE_2D);
            buffer.begin(GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(centerX + distX, centerY + distX, 0.0).color(1f, 0f, 0f, 1f).endVertex();
            tessellator.draw();
            glEnable(GL_TEXTURE_2D);
        }


        // compass directions
        fr.drawStringWithShadow("N", centerX - (fr.getStringWidth("N") / 2f), top + 1f, 0xff0000);
        fr.drawStringWithShadow("S", centerX - (fr.getStringWidth("S") / 2f), bottom - fr.FONT_HEIGHT - 1f, -1);
        fr.drawStringWithShadow("E", centerX - (radarSize / 2f) + 1f, centerY - (fr.FONT_HEIGHT / 2f), -1);
        fr.drawStringWithShadow("W", centerX + (radarSize / 2f) - fr.getStringWidth("W"), centerY - (fr.FONT_HEIGHT / 2f), -1);
    }

    @SubscribeEvent
    public void onReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            lastPacketTime = System.currentTimeMillis();
        }
    }
}
