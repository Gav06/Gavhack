package dev.gavhack.util;

import net.minecraft.client.renderer.GlStateManager;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {

    public static void prepareGL() {
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
    }

    public static void releaseGL() {
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
