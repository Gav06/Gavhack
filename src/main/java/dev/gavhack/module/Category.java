package dev.gavhack.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    PLAYER("Player"),
    OTHER("Other"),
    CLIENT("Client"),
    HUD("Hud");

    private final String name;
    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
