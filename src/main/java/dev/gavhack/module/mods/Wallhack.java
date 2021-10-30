package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.ForgeModContainer;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;
import java.util.Set;

public class Wallhack extends Module {

    public static int OPACITY = 128;

    public static Set<Block> blocks = new HashSet<>();

    public Wallhack() {
        super("Wallhack", "Peep through walls :^)", Category.RENDER);
        setBind(Keyboard.KEY_X);

        blocks.add(Blocks.DIAMOND_ORE);
        blocks.add(Blocks.REDSTONE_ORE);
        blocks.add(Blocks.IRON_ORE);
        blocks.add(Blocks.LAPIS_ORE);
        blocks.add(Blocks.COAL_ORE);
        blocks.add(Blocks.GOLD_ORE);
        blocks.add(Blocks.QUARTZ_ORE);
        blocks.add(Blocks.EMERALD_ORE);
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
