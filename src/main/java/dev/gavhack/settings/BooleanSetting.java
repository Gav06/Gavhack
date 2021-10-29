package dev.gavhack.settings;

public class BooleanSetting extends Setting {

    private final boolean defaultValue;
    private boolean value;

    public BooleanSetting(String name, boolean value) {
        super(name);
        this.value = value;
        this.defaultValue = value;
    }

    public void toggle() {
        this.value = !this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean isEnabled() {
        return value;
    }

    public void reset() {
        this.value = this.defaultValue;
    }
}
