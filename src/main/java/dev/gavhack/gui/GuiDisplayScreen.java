package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiDisplayScreen extends GuiScreen {


    private final List<AbstractComponent> components;

    public GuiDisplayScreen(ModuleManager modManager) {
        this.components = new ArrayList<>();
        int x = 2;
        for (Category category : Category.values()) {
            if (category != Category.HUD) {
                final Panel panel = new Panel(category, x, 2, 110, 12);
                x += 112;
                int y = 0;
                for (Module module : modManager.getCategoryModules(category)) {
                    panel.getButtons().add(new Button<>(module, panel.x, panel.y + panel.height + y, panel.width, panel.height));
                    y += panel.height;
                }

                components.add(panel);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        components.forEach(c -> c.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        components.forEach(c -> c.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        components.forEach(c -> c.draw(mouseX, mouseY, partialTicks));
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) throws IOException {
        super.keyTyped(keyChar, keyCode);
        components.forEach(c -> c.keyTyped(keyCode));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public List<AbstractComponent> getComponents() {
        return components;
    }

    public static void clickSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
