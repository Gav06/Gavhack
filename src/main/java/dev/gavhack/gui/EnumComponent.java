package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.settings.EnumSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class EnumComponent extends AbstractComponent {

    private final EnumSetting<?> setting;

    public EnumComponent(EnumSetting<?> setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY)) {
            if (mouseButton == 0) {
                setting.increment();
            } else if (mouseButton == 1) {
                setting.decrement();
            }
            GuiDisplayScreen.clickSound();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, 0xAC000000);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(setting.getName() + ": " + setting.getValue().toString(), x + 4f, y + 2f, -1);
    }

    @Override
    public void keyTyped(int keyCode) { }

    public EnumSetting<?> getSetting() {
        return setting;
    }
}
