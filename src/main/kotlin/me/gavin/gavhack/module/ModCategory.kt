package me.gavin.gavhack.module

enum class ModCategory(val title: String, val iconPath: String) {
    COMBAT("Combat", "gavhack/combat.png"),
    MOVEMENT("Movement", "gavhack/movement.png"),
    RENDER("Render", "gavhack/render.png"),
    WORLD("World", "gavhack/world.png"),
    MISC("Misc.", "gavhack/misc.png"),
    GUI("GUI", "gavhack/gui.png")
}