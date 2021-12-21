package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;

public class Timer extends Module {

    public static final FloatSetting speed = new FloatSetting("Multiplier", 2.0f, 0.1f, 10f);

    public Timer() {
        super("Timer", "change the game's tick rate", Category.MOVEMENT);
    }

    @Override
    public String getExtraInfo() {
        return "[" + speed.getValue() + "]";
    }
}
