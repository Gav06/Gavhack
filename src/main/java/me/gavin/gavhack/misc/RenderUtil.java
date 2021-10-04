package me.gavin.gavhack.misc;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL32;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil implements MC {

    private static ICamera camera = new Frustum();

    public static float[] getCornerPositions(Entity e, float partialTicks, int width, int height) {
        float x = -1;
        float y = -1;
        float w = width + 1;
        float h = height + 1;


        final Vec3d pos = new Vec3d(
                MathHelper.clampedLerp(e.lastTickPosX, e.posX, partialTicks),
                MathHelper.clampedLerp(e.lastTickPosY, e.posY, partialTicks),
                MathHelper.clampedLerp(e.lastTickPosZ, e.posZ, partialTicks));

        AxisAlignedBB bb = e.getEntityBoundingBox();

        if (e instanceof EntityEnderCrystal) {
            bb = new AxisAlignedBB(bb.minX + 0.3f, bb.minY + 0.2f, bb.minZ + 0.3f, bb.maxX - 0.3f, bb.maxY, bb.maxZ - 0.3f);
        }

        if (e instanceof EntityItem) {
            bb = new AxisAlignedBB(bb.minX, bb.minY + 0.7f, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
        }

        bb = bb.expand(0.15f, 0.1f, 0.15f);

        camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

        if (!camera.isBoundingBoxInFrustum(bb)) {
            return null;
        }

        final Vec3d[] corners = {
                new Vec3d(bb.minX - bb.maxX + e.width / 2, 0, bb.minZ - bb.maxZ + e.width / 2),
                new Vec3d(bb.maxX - bb.minX - e.width / 2, 0, bb.minZ - bb.maxZ + e.width / 2),
                new Vec3d(bb.minX - bb.maxX + e.width / 2, 0, bb.maxZ - bb.minZ - e.width / 2),
                new Vec3d(bb.maxX - bb.minX - e.width / 2, 0, bb.maxZ - bb.minZ - e.width / 2),

                new Vec3d(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
                new Vec3d(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
                new Vec3d(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2),
                new Vec3d(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2)
        };

        for (Vec3d vec : corners) {
            final Vec3d projection = ProjectionUtil.toScaledScreenPos(new Vec3d(
                    pos.x + vec.x,
                    pos.y + vec.y,
                    pos.z + vec.z));

            x = Math.max(x, (float) projection.x);
            y = Math.max(y, (float) projection.y);

            w = Math.min(w, (float) projection.x);
            h = Math.min(h, (float) projection.y);
        }

        if (x != -1 && y != -1 && w != width + 1 && h != height + 1) {
            return new float[]{x, y, w, h};
        }

        return null;
    }

    public static void prepare3d() {
        glPushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        glEnable(GL32.GL_DEPTH_CLAMP);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
    }

    public static void release3d() {
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
        GlStateManager.disableAlpha();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        glDisable(GL32.GL_DEPTH_CLAMP);
        glPopMatrix();
    }
}
