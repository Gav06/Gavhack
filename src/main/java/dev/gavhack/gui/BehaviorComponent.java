package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.settings.BehaviorSetting;

public class BehaviorComponent extends AbstractComponent {

    private final BehaviorSetting setting;

    public BehaviorComponent(BehaviorSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            setting.getBehavior().execute();
            GuiDisplayScreen.clickSound();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
    }

    @Override
    public void keyTyped(int keyCode) {

    }

    public BehaviorSetting getSetting() {
        return setting;
    }
}
