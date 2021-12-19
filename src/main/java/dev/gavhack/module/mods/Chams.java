package dev.gavhack.module.mods;

import dev.gavhack.events.RenderModelEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {

    private final EnumSetting<ChamsMode> mode = new EnumSetting<>("Mode", ChamsMode.Wireframe);

    public Chams() {
        super("Chams", "cham", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderModel(RenderModelEvent event) {
        event.setCanceled(true);

        if (mode.getValue() == ChamsMode.Normal) {
            GL11.glPushMatrix();
            GL11.glDepthRange(0.0, 0.01);
            event.render();
            GL11.glDepthRange(0.0, 1.0);
            GL11.glPopMatrix();
        } else if (mode.getValue() == ChamsMode.Wireframe) {
            GL11.glPushMatrix();
            GlStateManager.color(1f, 1f, 1f, 1f);
            GL11.glDepthRange(0.0, 0.01);
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            event.render();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            GL11.glDepthRange(0.0, 1.0);
            GL11.glPopMatrix();
        } else if (mode.getValue() == ChamsMode.XQZ) {
            GL11.glPushMatrix();
            GlStateManager.color(1f, 0f, 1f);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            event.render();
            GlStateManager.enableDepth();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1f, 1f, 1f);
            event.render();
            GL11.glPopMatrix();
        }
    }

    private enum ChamsMode {
        Normal,
        XQZ,
        Wireframe
    }
}