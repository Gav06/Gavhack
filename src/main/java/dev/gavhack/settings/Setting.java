package dev.gavhack.settings;

public abstract class Setting {

    private final String name;

    public Setting(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void reset();
}
