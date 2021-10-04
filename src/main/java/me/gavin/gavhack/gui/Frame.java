package me.gavin.gavhack.gui;

import me.gavin.gavhack.Gavhack;
import me.gavin.gavhack.mods.api.Module;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class Frame extends DragComponent {

    protected boolean isOpen;
    private final Module.Category category;
    private final ArrayList<Button> buttons;

    public Frame(Module.Category category, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.category = category;
        this.buttons = new ArrayList<>();

        for (Module module : Gavhack.INSTANCE.getModuleManager().getCategoryModules(category)) {
            buttons.add(new Button(module, 0, 0, 110, 14));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 1 && inside(mouseX, mouseY)) {
            this.isOpen = !isOpen;
            clickSound();
            return;
        }

        if (isOpen) {
            for (Button button : buttons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);

        if (isOpen) {
            for (Button button : buttons) {
                button.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        Gui.drawRect(x, y, x + width, y + height, 0xc8ff0000);
        fr.drawStringWithShadow(category.name(), x + (width / 2f) - (fr.getStringWidth(category.name()) / 2f), y + (height / 2f) - (fr.FONT_HEIGHT / 2f), -1);


        if (isOpen) {
            int yOffset = height;
            for (Button button : buttons) {
                button.x = x;
                button.y = y + yOffset;
                button.draw(mouseX, mouseY, partialTicks);
                yOffset += button.height + button.getAdditionalHeight();
            }
        }
    }

    @Override
    public void keyTyped(int keyCode) {
        super.keyTyped(keyCode);

        if (isOpen) {
            for (Button button : buttons) {
                button.keyTyped(keyCode);
            }
        }
    }

    public Module.Category getCategory() {
        return category;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
