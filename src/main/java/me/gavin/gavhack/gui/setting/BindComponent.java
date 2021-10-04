package me.gavin.gavhack.gui.setting;

import me.gavin.gavhack.gui.AbstractComponent;
import me.gavin.gavhack.misc.Bind;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class BindComponent extends AbstractComponent {

    private boolean isListening;
    private final Bind bind;

    public BindComponent(Bind bind, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.bind = bind;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        Gui.drawRect(x, y, x + width, y + height, 0xc8000000);
        final String text = isListening ? "Press a key..." : "Bind: [" + bind.getBindString() + "]";
        fr.drawStringWithShadow(text, x + 3f, y + 3f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (inside(mouseX, mouseY) && mouseButton == 0) {
            isListening = !isListening;
            clickSound();
        }
    }

    @Override
    public void keyTyped(int keyCode) {
        if (isListening) {
            if (keyCode == Keyboard.KEY_DELETE) {
                bind.setBind(0);
            } else {
                bind.setBind(keyCode);
            }

            isListening = false;
        }
    }

    public Bind getBind() {
        return bind;
    }
}
