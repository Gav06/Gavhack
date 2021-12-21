package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.settings.EnumSetting;

public class Freecam extends Module {

    private final EnumSetting<FreecamMode> mode = new EnumSetting<>("Mode", FreecamMode.Old);
    private final BooleanSetting cancelPackets = new BooleanSetting("CancelPackets", true);

    public Freecam() {
        super("Freecam", "Look around freely", Category.PLAYER);
    }

    public enum FreecamMode {
        Camera,
        Old
    }
}
