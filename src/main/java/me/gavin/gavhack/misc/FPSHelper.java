package me.gavin.gavhack.misc;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FPSHelper {

    public static final FPSHelper INSTANCE = new FPSHelper();

    public FPSHelper() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private int prevFPS = -1;
    private int fps;

    private float lerpFPS;

    private int frames = 0;
    private long lastUpdateTime = 0L;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (System.currentTimeMillis() - lastUpdateTime >= 1000L) {
            prevFPS = fps;
            fps = frames;
            lastUpdateTime = System.currentTimeMillis();
            frames = 0;
        }

        frames++;

        lerpFPS = (float) MathHelper.clampedLerp(prevFPS, fps, (System.currentTimeMillis() - lastUpdateTime) / 1000.0f) / 2.0f;
    }

    public float getFPS() {
        return lerpFPS;
    }
}
