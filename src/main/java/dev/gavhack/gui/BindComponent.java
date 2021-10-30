package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class BindComponent extends AbstractComponent {

    private final KeyBinding bind;
    private boolean binding = false;

    public BindComponent(KeyBinding binding, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.bind = binding;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            binding = !binding;
            GuiDisplayScreen.clickSound();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, 0xAC000000);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Bind: " + Keyboard.getKeyName(bind.getKeyCode()), x + 2f, y + 2f, -1);
    }

    @Override
    public void keyTyped(int keyCode) {
        if (binding) {
            if (keyCode != Keyboard.KEY_DELETE && keyCode != Keyboard.KEY_NONE) {
                bind.setKeyCode(keyCode);
            } else {
                bind.setKeyCode(Keyboard.KEY_NONE);
            }
            binding = false;
            GuiDisplayScreen.clickSound();
        }
    }

    public KeyBinding getBind() {
        return bind;
    }
}
