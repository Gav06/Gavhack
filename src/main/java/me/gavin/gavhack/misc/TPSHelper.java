package me.gavin.gavhack.misc;

import me.gavin.gavhack.events.PacketEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class TPSHelper {

    public static final TPSHelper INSTANCE = new TPSHelper();

    public TPSHelper() {
        MinecraftForge.EVENT_BUS.register(this);
        this.prevTime = -1L;
        Arrays.fill(ticks, 0f);
    }

    private long prevTime;
    private final float[] ticks = new float[20];
    private int currentTick;

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (prevTime != -1) {
                ticks[currentTick % ticks.length] = MathHelper.clamp(20f / ((System.currentTimeMillis() - prevTime) / 1000.0f), 0f, 20f);
                currentTick++;
            }

            prevTime = System.currentTimeMillis();
        }
    }

    public float getTPS() {
        int tickCount = 0;
        float tickRate = 0f;

        for (float tick : ticks) {
            if (tick > 0f) {
                tickRate += tick;
                tickCount++;
            }
        }

        return MathHelper.clamp(tickRate / tickCount, 0f, 20f);
    }
}
