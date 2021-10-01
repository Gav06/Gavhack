package me.gavin.gavhack.gui;

import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public final class GavhackGui extends GuiScreen {

    private final ArrayList<AbstractComponent> components;

    public GavhackGui() {
        this.components = new ArrayList<>();

        components.add(new TestComponent(50, 50, 100, 100));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (AbstractComponent component : components) {
            component.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (AbstractComponent component : components) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (AbstractComponent component : components) {
            component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) throws IOException {
        super.keyTyped(keyChar, keyCode);
    }

    public ArrayList<AbstractComponent> getComponents() {
        return components;
    }
}
