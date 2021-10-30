package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.util.shader.FramebufferShader;
import dev.gavhack.util.shader.shaders.GalaxyShader;
import dev.gavhack.util.shader.shaders.RedShader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP extends Module {
    public ESP() {
        super("ESP", "shader esp", Category.RENDER);
    }

    private final FramebufferShader shader = GalaxyShader.GALAXY_SHADER;

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();

        shader.startDraw(event.getPartialTicks());
        for (Entity e : mc.world.loadedEntityList) {
            if (e.equals(mc.player))
                continue;

            mc.getRenderManager().renderEntityStatic(e, event.getPartialTicks(), false);
        }
        shader.stopDraw();


        GlStateManager.color(1f, 1f, 1f);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
    }
}
