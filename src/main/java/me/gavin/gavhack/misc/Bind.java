package me.gavin.gavhack.misc;

import org.lwjgl.input.Keyboard;

public class Bind {

    private int bind;

    public Bind() {
        this.bind = Keyboard.KEY_NONE;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public int getBind() {
        return bind;
    }

    public String getBindString() {
        return Keyboard.getKeyName(bind);
    }
}
