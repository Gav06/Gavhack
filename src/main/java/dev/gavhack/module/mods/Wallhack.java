package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraftforge.common.ForgeModContainer;
import org.lwjgl.input.Keyboard;

public class Wallhack extends Module {

    public static int OPACITY = 128;

    public Wallhack() {
        super("Wallhack", "Peep through walls :^)", Category.RENDER);
        setBind(Keyboard.KEY_X);
    }

    @Override
    protected void onEnable() {
        ForgeModContainer.forgeLightPipelineEnabled = false;
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        ForgeModContainer.forgeLightPipelineEnabled = true;
        mc.renderGlobal.loadRenderers();
    }
}
