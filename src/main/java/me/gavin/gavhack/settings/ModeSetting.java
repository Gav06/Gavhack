package me.gavin.gavhack.settings;

public class ModeSetting extends Setting {

    private final String[] modes;
    private int index;

    public ModeSetting(String name, String mode, String... modes) {
        super(name);
        this.modes = modes;
        this.index = indexOf(mode);
    }

    public String getMode() {
        return modes[index];
    }

    public void increment() {
        if (index == modes.length - 1) {
            index = 0;
        } else {
            index++;
        }
    }

    public void decrement() {
        if (index == 0) {
            index = modes.length - 1;
        } else {
            index--;
        }
    }

    public int indexOf(String mode) {
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].equals(mode))
                return i;
        }

        return -1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
