package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.FloatSetting;

public class Timer extends Module {

    public final FloatSetting speed = new FloatSetting("TickLength", 1.0f, 0.01f, 5.0f);

    public Timer() {
        super("Timer", "Change how the game time moves", Category.World);
        this.settings.add(speed);
    }
}