package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;

public class PortalTweaks extends Module {

    public static BooleanSetting hitbox;
    public static BooleanSetting chat;

    public PortalTweaks() {
        super("PortalTweaks", "modify portal behavior", Category.PLAYER);
        hitbox = new BooleanSetting("Hitbox", true);
        chat = new BooleanSetting("Gui's", true);
    }
}
