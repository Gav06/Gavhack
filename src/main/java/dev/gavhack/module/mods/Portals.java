package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;

public class Portals extends Module {

    public static BooleanSetting hitbox;
    public static BooleanSetting chat;

    public Portals() {
        super("Portals", "modify portal behavior", Category.PLAYER);
        hitbox = new BooleanSetting("Hitbox", true);
        chat = new BooleanSetting("Chat", true);
    }
}
