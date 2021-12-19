package dev.gavhack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class MathUtil {

    public static Vec3d getLerpPos(Entity entity, float partialTicks) {
        return new Vec3d(
                MathHelper.clampedLerp(entity.lastTickPosX, entity.posX, partialTicks),
                MathHelper.clampedLerp(entity.lastTickPosY, entity.posY, partialTicks),
                MathHelper.clampedLerp(entity.lastTickPosZ, entity.posZ, partialTicks)
        );
    }

    public static Vector4f hexToRGB(int color) {
        float r = (float) (color >> 16 & 0xFF) / 255.0F;
        float g = (float) (color >> 8 & 0xFF) / 255.0F;
        float b = (float) (color & 0xFF) / 255.0F;
        float a = (float) (color >> 24 & 0xFF) / 255.0F;
        return new Vector4f(r, g, b, a);
    }

    public static int colorToRGB(Color color) {
        return colorToRGB(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int colorToRGB(int red, int green, int blue, int alpha) {
        int rgb = (alpha << 24);
        rgb |= (red << 16);
        rgb |= (green << 8);
        rgb |= (blue);

        return rgb;
    }

    public static int colorToRGB(float red, float green, float blue, float alpha) {
        return colorToRGB((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255));
    }

    public static Vec2f[] getCirclePoints(float radius, int points) {
        final Vec2f[] arr = new Vec2f[points];

        for (int i = 0; i < points; i++) {
            final float angle = (float) Math.toRadians(((double) i / points) * 360.0d);
            arr[i] = new Vec2f((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
        }

        return arr;
    }

    // original code :sweat:

    public static double[] directionSpeed(double speed, EntityPlayerSP player, float partialTicks) {
        float forward = player.movementInput.moveForward;
        float side = player.movementInput.moveStrafe;
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;

        if (forward != 0) {
            if (side > 0) {
                yaw += (forward > 0 ? -45 : 45);
            } else if (side < 0) {
                yaw += (forward > 0 ? 45 : -45);
            }
            side = 0;

            //forward = clamp(forward, 0, 1);
            if (forward > 0) {
                forward = 1;
            } else if (forward < 0) {
                forward = -1;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90));
        final double cos = Math.cos(Math.toRadians(yaw + 90));
        final double posX = (forward * speed * cos + side * speed * sin);
        final double posZ = (forward * speed * sin - side * speed * cos);
        return new double[]{posX, posZ};
    }

    public static double radToDeg(double rad) {
        return rad * (float) (180.0f / Math.PI);
    }

    public static double degToRad(double deg) {
        return deg * (float) (Math.PI / 180.0f);
    }

    public static Vec3d direction(float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90f)), 0, Math.sin(degToRad(yaw + 90f)));
    }

    public static float[] calcAngle(Vec3d from, Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0F;
        final double difZ = to.z - from.z;

        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);

        return new float[]{(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0f), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)))};
    }

    public static ArrayList<BlockPos> getSurroundingBlocks(int distance, boolean applyMotion, EntityPlayer player) {
        final Vec3d playerVec = player.getPositionVector();
        if (applyMotion) {
            playerVec.add(player.motionX, player.motionY, player.motionZ);
        }

        final ArrayList<BlockPos> posList = new ArrayList<>();

        for (int x = -distance; x < distance; x++) {
            for (int y = -distance; y < distance; y++) {
                for (int z = -distance; z < distance; z++) {
                    posList.add(new BlockPos(x, y, z).add(playerVec.x, playerVec.y, playerVec.z));
                }
            }
        }

        return posList;
    }
}