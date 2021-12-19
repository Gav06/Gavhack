package dev.gavhack.gui;

import com.google.gson.JsonObject;
import dev.gavhack.Gavhack;
import dev.gavhack.config.Configurable;
import dev.gavhack.gui.api.DragComponent;
import dev.gavhack.module.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public class Panel extends DragComponent implements Configurable {

    private final List<Button<?>> buttons;
    private final Category category;

    private boolean open = true;

    public Panel(Category category, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.buttons = new ArrayList<>();
        this.category = category;
        Gavhack.INSTANCE.getConfigManager().addConfigurable(this);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        Gui.drawRect(x, y, x + width, y + height, 0xc8000000);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(S + "a" + category.toString() + S + "f (" + buttons.size() + ")", x + 2f, y + 2f, -1);
        if (open) {

            int yAdd = 0;
            for (Button<?> button : buttons) {
                button.x = x;
                button.y = y + height + yAdd;
                yAdd += button.height + button.getSettingHeight();
                button.draw(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (inside(mouseX, mouseY) && mouseButton == 1) {
            open = !open;
            GuiDisplayScreen.clickSound();
        }
        if (open) {
            buttons.forEach(b -> b.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (open) {
            buttons.forEach(b -> b.mouseReleased(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void keyTyped(int keyCode) {
        if (open) {
            buttons.forEach(b -> b.keyTyped(keyCode));
        }
    }

    public List<Button<?>> getButtons() {
        return buttons;
    }

    @Override
    public String getConfigName() {
        return category.toString() + "Panel";
    }

    @Override
    public void writeToConfig(JsonObject jsonObject) {
        jsonObject.addProperty("x", x);
        jsonObject.addProperty("y", y);
        jsonObject.addProperty("open", open);
    }

    @Override
    public void readFromConfig(JsonObject jsonObject) {
        this.x = jsonObject.get("x").getAsInt();
        this.y = jsonObject.get("y").getAsInt();
        this.open = jsonObject.get("open").getAsBoolean();
    }
}
