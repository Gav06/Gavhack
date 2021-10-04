package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGui", "Opens the gui", Category.Client);
        setBind(Keyboard.KEY_RSHIFT);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(gavhack.getGui());
        disable();
    }
}