package dev.gavhack.gui.hud;

import dev.gavhack.gui.api.DragComponent;

public class HudComponent extends DragComponent {

    private HudModule module;

    public HudComponent(HudModule hudModule, int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
    }

    @Override
    public void keyTyped(int keyCode) { }
}
