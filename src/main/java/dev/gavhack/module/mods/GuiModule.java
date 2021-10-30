package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import org.lwjgl.input.Keyboard;

public class GuiModule extends Module {

    public GuiModule() {
        super("Gui", "Opens the client GUI", Category.CLIENT);
        setBindKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(gavhack.getGui());
        disable();
    }
}
