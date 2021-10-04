package me.gavin.gavhack.gui;

import it.unimi.dsi.fastutil.booleans.BooleanSet;
import me.gavin.gavhack.gui.setting.BindComponent;
import me.gavin.gavhack.gui.setting.BooleanComponent;
import me.gavin.gavhack.gui.setting.SliderComponent;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.BooleanSetting;
import me.gavin.gavhack.settings.FloatSetting;
import me.gavin.gavhack.settings.Setting;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class Button extends AbstractComponent {

    protected boolean isOpen;
    private final Module module;
    private final ArrayList<AbstractComponent> settings = new ArrayList<>();

    public Button(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;

        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                settings.add(new BooleanComponent((BooleanSetting) setting, 0, 0, width, height));
            } else if (setting instanceof FloatSetting) {
                settings.add(new SliderComponent((FloatSetting) setting, 0, 0, width, height));
            }
        }

        settings.add(new BindComponent(module, 0, 0, width, height));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (inside(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
                clickSound();
            } else if (mouseButton == 1) {
                isOpen = !isOpen;
                clickSound();
            }
        }

        if (isOpen) {
            for (AbstractComponent component : settings) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);

        if (isOpen) {
            for (AbstractComponent component : settings) {
                component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        Gui.drawRect(x, y, x + width, y + height, 0x80000000);
        fr.drawStringWithShadow(module.getName(), x + 3f, y + 3f, module.isEnabled() ? -1 : 0x787878);
        final String text = isOpen ? "-" : "+";
        fr.drawStringWithShadow(text, x + width - fr.getStringWidth(text) - 1f, y + 3f, -1);

        if (isOpen) {
            int yOffset = height;
            for (AbstractComponent component : settings) {
                component.x = x;
                component.y = y + yOffset;
                yOffset += component.height;
                component.draw(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void keyTyped(int keyCode) {
        super.keyTyped(keyCode);

        if (isOpen) {
            for (AbstractComponent component : settings) {
                component.keyTyped(keyCode);
            }
        }
    }

    public Module getModule() {
        return module;
    }

    public ArrayList<AbstractComponent> getSettings() {
        return settings;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getAdditionalHeight() {
        if (isOpen) {
            int val = 0;
            for (AbstractComponent component : settings) {
                val += component.height;
            }

            return val;
        } else {
            return 0;
        }
    }
}