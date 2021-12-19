package dev.gavhack.util;

public class MilliTimer {

    private long lastMillis = System.currentTimeMillis();

    public void reset() {
        this.lastMillis = System.currentTimeMillis();
    }

    public boolean hasPassed(long ms) {
        return System.currentTimeMillis() - lastMillis > ms;
    }
}
