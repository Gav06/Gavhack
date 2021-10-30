package dev.gavhack.gui.hud;

import dev.gavhack.gui.Panel;
import dev.gavhack.module.Category;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class HudDisplayScreen extends GuiScreen {

    private final Panel hudPanel;

    private final List<HudComponent> hudComponents;

    public HudDisplayScreen() {
        this.hudPanel = new Panel(Category.HUD, 2, 2, 110, 12);
        this.hudComponents = new ArrayList<>();
    }

    public List<HudComponent> getHudComponents() {
        return hudComponents;
    }
}
