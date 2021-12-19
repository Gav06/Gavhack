package dev.gavhack.settings;

public class BehaviorSetting extends Setting {

    private final Behavior behavior;

    public BehaviorSetting(String name, Behavior behavior) {
        super(name);
        this.behavior = behavior;
    }

    @Override
    public void reset() { }

    public Behavior getBehavior() {
        return behavior;
    }

    @FunctionalInterface
    public interface Behavior {

        void execute();
    }
}
