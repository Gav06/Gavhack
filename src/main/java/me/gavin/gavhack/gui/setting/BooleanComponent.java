package me.gavin.gavhack.gui.setting;

import me.gavin.gavhack.gui.AbstractComponent;
import me.gavin.gavhack.settings.BooleanSetting;
import net.minecraft.client.gui.Gui;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

public class BooleanComponent extends AbstractComponent {

    private final BooleanSetting setting;

    public BooleanComponent(BooleanSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            setting.toggle();
            clickSound();
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        Gui.drawRect(x, y, x + width, y + height, 0xc8000000);
        fr.drawStringWithShadow(setting.getName() + ": " + (setting.isEnabled() ? GREEN + "true" : RED + "false"), x + 3f, y + 3f, -1);
    }

    public BooleanSetting getSetting() {
        return setting;
    }
}
