package dev.gavhack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {

    private static final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

    public static Vec3d getRenderOffset() {
        return new Vec3d(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
    }

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
