package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;

public class GuiMove extends Module {
    public GuiMove() {
        super("GuiMove", "Move while in a gui", Category.MOVEMENT);
        enable();
    }
}
