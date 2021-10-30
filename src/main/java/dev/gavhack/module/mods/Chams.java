package dev.gavhack.module.mods;

import dev.gavhack.events.RenderModelEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {

    private final EnumSetting<ChamMode> mode = new EnumSetting<>("Mode", ChamMode.Wireframe);

    public Chams() {
        super("Chams", "cham", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderModel(RenderModelEvent event) {
        event.setCanceled(true);

        if (mode.getValue() == ChamMode.Normal) {
            GL11.glDepthRange(0.0, 0.001);
            event.render();
            GL11.glDepthRange(0.0, 1.0);
        } else if (mode.getValue() == ChamMode.Wireframe) {
            GL11.glDepthRange(0.0, 0.001);
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.color(1f, 1f, 1f, 1f);
            event.render();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            GL11.glDepthRange(0.0, 1.0);
        }
    }

    private enum ChamMode {
        Normal,
        XQZ,
        Wireframe
    }
}
