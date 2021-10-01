package me.gavin.gavhack.gui;

public abstract class AbstractComponent extends Rect implements IComponent {
    public AbstractComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) { }

    @Override
    public void keyTyped(int keyCode) { }
}
