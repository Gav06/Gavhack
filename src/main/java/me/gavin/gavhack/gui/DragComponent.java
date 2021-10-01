package me.gavin.gavhack.gui;

public abstract class DragComponent extends AbstractComponent {
    public DragComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    private int dragX, dragY;
    private boolean dragging;

    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            x = (dragX - mouseX);
            y = (dragY - mouseY);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0)
            dragging = false;
    }
}
