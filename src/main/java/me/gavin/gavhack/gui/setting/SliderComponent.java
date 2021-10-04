package me.gavin.gavhack.gui.setting;

import me.gavin.gavhack.gui.AbstractComponent;
import me.gavin.gavhack.misc.MathUtil;
import me.gavin.gavhack.settings.FloatSetting;
import net.minecraft.client.gui.Gui;

public class SliderComponent extends AbstractComponent {

    private final FloatSetting setting;

    protected boolean draggingSlider;
    protected float sliderWidth;

    public SliderComponent(FloatSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        sliderLogic(mouseX);

        Gui.drawRect(x, y, x + width, y + height, 0xc8000000);
        Gui.drawRect(x, y, x + (int)sliderWidth, y + height, 0x70ff0000);
        fr.drawStringWithShadow(setting.getName() + ": " + setting.getValue(), x + 3f, y + 3f, -1);
    }

    private void sliderLogic(int mouseX) {
        final float difference = Math.min(width, Math.max(0, mouseX - x));
        final float min = setting.getMin();
        final float max = setting.getMax();
        final float value = setting.getValue();
        sliderWidth = width * (value - min) / (max - min);
        if (draggingSlider) {
            if (difference == 0) {
                setting.setValue(min);
            } else {
                float val = MathUtil.roundNumber(difference / width * (max - min) + min, 2);
                setting.setValueClamped(val);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            draggingSlider = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            draggingSlider = false;
        }
    }

    public FloatSetting getSetting() {
        return setting;
    }
}
