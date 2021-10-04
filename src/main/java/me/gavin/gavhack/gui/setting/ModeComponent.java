package me.gavin.gavhack.gui.setting;

import me.gavin.gavhack.gui.AbstractComponent;
import me.gavin.gavhack.settings.ModeSetting;
import net.minecraft.client.gui.Gui;

import static com.mojang.realmsclient.gui.ChatFormatting.GREEN;
import static com.mojang.realmsclient.gui.ChatFormatting.RED;

public class ModeComponent extends AbstractComponent {

    private final ModeSetting setting;

    public ModeComponent(ModeSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        Gui.drawRect(x, y, x + width, y + height, 0xc8000000);
        fr.drawStringWithShadow(setting.getName() + ": " + setting.getMode(), x + 3f, y + 3f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (inside(mouseX, mouseY)) {
            if (mouseButton == 0) {
                setting.increment();
                clickSound();
            } else if (mouseButton == 1) {
                setting.decrement();
                clickSound();
            }
        }
    }

    public ModeSetting getSetting() {
        return setting;
    }
}
