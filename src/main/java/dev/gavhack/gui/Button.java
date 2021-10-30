package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button<T extends Module> extends AbstractComponent {

    private final T module;
    private boolean open;

    public Button(T module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 1) {
                open = !open;
            }
            GuiDisplayScreen.clickSound();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, 0xAA000000);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((module.isEnabled() || inside(mouseX, mouseY) ? S + "a" : "") + module.getName(), x + 2f, y + 2f, -1);
    }

    @Override
    public void keyTyped(int keyCode) {

    }

    public Module getModule() {
        return module;
    }
}