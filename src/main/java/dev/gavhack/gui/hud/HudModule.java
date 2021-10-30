package dev.gavhack.gui.hud;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;

public abstract class HudModule extends Module {

    protected HudComponent component;

    public HudModule(String name, String description) {
        super(name, description, Category.HUD);
        this.component = new HudComponent(this, 112, 2, 0, 0);
    }

}
