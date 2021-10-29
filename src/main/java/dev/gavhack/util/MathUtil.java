package dev.gavhack.util;

import net.minecraft.util.math.Vec2f;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;

public class MathUtil {

    public static Vector4f hexToRGB(int color) {
        float r = (float)(color >> 16 & 0xFF) / 255.0F;
        float g = (float)(color >> 8 & 0xFF) / 255.0F;
        float b = (float)(color & 0xFF) / 255.0F;
        float a = (float)(color >> 24 & 0xFF) / 255.0F;
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
}