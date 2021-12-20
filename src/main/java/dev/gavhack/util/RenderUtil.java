package dev.gavhack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.AxisAlignedBB;
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

    public static void drawImmediateBox(AxisAlignedBB aabb, float r, float g, float b) {
        final double minX = aabb.minX;
        final double minY = aabb.minY;
        final double minZ = aabb.minZ;
        final double maxX = aabb.maxX;
        final double maxY = aabb.maxY;
        final double maxZ = aabb.maxZ;

        glBegin(GL_LINE_STRIP);
        glColor4f(r, g, b, 0f);
        glVertex3d(minX, minY, minZ);
        glColor4f(r, g, b, 1f);
        glVertex3d(minX, minY, minZ);
        glVertex3d(maxX, minY, minZ);
        glVertex3d(maxX, minY, maxZ);
        glVertex3d(minX, minY, maxZ);
        glVertex3d(minX, minY, minZ);
        glVertex3d(minX, maxY, minZ);
        glVertex3d(maxX, maxY, minZ);
        glVertex3d(maxX, maxY, maxZ);
        glVertex3d(minX, maxY, maxZ);
        glVertex3d(minX, maxY, minZ);
        glColor4f(r, g, b, 0f);
        glVertex3d(minX, maxY, maxZ);
        glColor4f(r, g, b, 1f);
        glVertex3d(minX, minY, maxZ);
        glColor4f(r, g, b, 0f);
        glVertex3d(maxX, maxY, maxZ);
        glColor4f(r, g, b, 1f);
        glVertex3d(maxX, minY, maxZ);
        glColor4f(r, g, b, 0f);
        glVertex3d(maxX, maxY, minZ);
        glColor4f(r, g, b, 1f);
        glVertex3d(maxX, minY, minZ);
        glColor4f(r, g, b, 0f);
        glVertex3d(maxX, minY, minZ);
        glEnd();
    }
}
