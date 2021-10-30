package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.block.Block;
import net.minecraftforge.common.ForgeModContainer;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;
import java.util.Set;

public class Wallhack extends Module {

    public static int OPACITY = 128;

    public static Set<Block> blocks = new HashSet<>();

    public Wallhack() {
        super("Wallhack", "Peep through walls :^)", Category.RENDER);
        setBindKey(Keyboard.KEY_X);
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
