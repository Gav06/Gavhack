package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.settings.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class BooleanComponent extends AbstractComponent {

    private final BooleanSetting setting;

    public BooleanComponent(BooleanSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            setting.toggle();
            GuiDisplayScreen.clickSound();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, 0xAC000000);
        final String str = setting.isEnabled() ? S + "atrue" : S + "cfalse";
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(setting.getName() + ": " + str, x + 4f, y + 2f, -1);
    }

    @Override
    public void keyTyped(int keyCode) { }

    public BooleanSetting getSetting() {
        return setting;
    }
}
