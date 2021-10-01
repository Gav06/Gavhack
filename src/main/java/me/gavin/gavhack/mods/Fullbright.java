package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Makes the world bright", Category.Render);
        setBind(Keyboard.KEY_B);
    }
}
