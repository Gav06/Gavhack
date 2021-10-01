package me.gavin.gavhack.settings;

import net.minecraft.util.math.MathHelper;

public class FloatSetting extends Setting {

    private final float min, max;
    private float value;

    public FloatSetting(String name, float value, float min, float max) {
        super(name);
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setValueClamped(float value) {
        this.value = MathHelper.clamp(value, this.min, this.max);
    }
}
