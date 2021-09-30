package me.gavin.gavhack.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.gui.ScaledResolution;

public abstract class RenderEvent implements Event {

    private final float partialTicks;

    public RenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public static final class World extends RenderEvent {

        public World(float partialTicks) {
            super(partialTicks);
        }
    }

    public static final class Screen extends RenderEvent {

        private final ScaledResolution scaledResolution;

        public Screen(float partialTicks, ScaledResolution scaledResolution) {
            super(partialTicks);
            this.scaledResolution = scaledResolution;
        }

        public ScaledResolution getScaledResolution() {
            return scaledResolution;
        }
    }
}
