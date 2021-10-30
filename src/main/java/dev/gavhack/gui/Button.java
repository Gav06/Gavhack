package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button<T extends Module> extends AbstractComponent {

    private final T module;

    public Button(T module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, 0xAA000000);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(module.getName(), x + 2f, y + 2f, module.isEnabled() ? 0x00ff00 : -1);
    }

    @Override
    public void keyTyped(int keyCode) {

    }

    public Module getModule() {
        return module;
    }
}
