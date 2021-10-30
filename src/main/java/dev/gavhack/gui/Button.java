package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public class Button<T extends Module> extends AbstractComponent {

    private final List<AbstractComponent> settings;
    private final T module;
    private boolean open;

    public Button(T module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.settings = new ArrayList<>();

        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                settings.add(new BooleanComponent((BooleanSetting) setting, 0, 0, width, height));
            } else if (setting instanceof EnumSetting<?>) {
                settings.add(new EnumComponent((EnumSetting<?>) setting, 0, 0, width, height));
            }
        }

        settings.add(new BindComponent(module.getKeyBinding(), 0, 0, width, height));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 1) {
                open = !open;
            }
            GuiDisplayScreen.clickSound();
        }

        if (open) {
            settings.forEach(s -> s.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (open) {
            settings.forEach(s -> s.mouseReleased(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, 0xAA000000);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((module.isEnabled() ? S + "a" : "") + module.getName(), (inside(mouseX, mouseY) ? 2f : 0f) + x + 2f, y + 2f, -1);

        if (open) {
            int yAdd = 0;
            for (AbstractComponent component : settings) {
                component.x = x;
                component.y = y + height + yAdd;
                yAdd += component.height;
                component.draw(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void keyTyped(int keyCode) {
        if (open) {
            settings.forEach(s -> s.keyTyped(keyCode));
        }
    }

    public Module getModule() {
        return module;
    }

    public List<AbstractComponent> getSettings() {
        return settings;
    }

    public int getSettingHeight() {
        if (open) {
            int i = 0;
            for (AbstractComponent component : settings) {
                i += component.height;
            }
            return i;
        } else {
            return 0;
        }
    }
}