package dev.gavhack.gui.api;

public abstract class AbstractComponent extends Rect {

    protected final char S = '\u00A7';

    public AbstractComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    public abstract void draw(int mouseX, int mouseY, float partialTicks);

    public abstract void keyTyped(int keyCode);
}
