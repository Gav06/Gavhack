package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatComponent extends AbstractComponent {

    private final FloatSetting setting;

    private boolean isDraggingSlider = false;
    private int sliderWidth;

    public FloatComponent(FloatSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            isDraggingSlider = true;
            GuiDisplayScreen.clickSound();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (isDraggingSlider && mouseButton == 0) {
            isDraggingSlider = false;
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        updateSlider(mouseX);

        Gui.drawRect(x, y, x + width, y + height, 0xAA000000);
        Gui.drawRect(x, y, x + sliderWidth, y + height, 0xAA55FF55);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(setting.getName() + ": " + setting.getValue(), x + 4f, y + 2f, -1);
    }

    @Override
    public void keyTyped(int keyCode) {
    }

    protected void updateSlider(int mouseX) {
        float diff = Math.min(width, Math.max(0, mouseX - x));
        float min = setting.getMin();
        float max = setting.getMax();
        sliderWidth = (int) (width * (setting.getValue() - min) / (max - min));
        if (isDraggingSlider) {
            if (diff == 0) {
                setting.setValue(setting.getMin());
            } else {
                float value = roundToPlace(diff / width * (max - min) + min, 1);
                setting.setValue(value);
            }
        }
    }

    public static float roundToPlace(float value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
