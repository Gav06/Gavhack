package dev.gavhack.settings;

import net.minecraft.util.math.MathHelper;

public class FloatSetting extends Setting {

    private final float defaultValue;
    private final float min;
    private final float max;
    private float value;

    public FloatSetting(String name, float value, float min, float max) {
        super(name);
        this.value = value;
        this.defaultValue = value;
        this.min = min;
        this.max = max;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setValueClamped(float value) {
        this.value = MathHelper.clamp(value, this.min, this.max);
    }

    public float getValue() {
        return this.value;
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    @Override
    public void reset() {
        this.value = this.defaultValue;
    }
}
