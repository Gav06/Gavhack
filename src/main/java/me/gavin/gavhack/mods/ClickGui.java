package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGui", "Opens the gui", Category.Client);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(gavhack.getGui());
    }
}
