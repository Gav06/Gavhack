package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BehaviorSetting;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.ForgeModContainer;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;
import java.util.Set;

public class Wallhack extends Module {

    public static Set<Block> blocks = new HashSet<>();

    public static final FloatSetting opacity = new FloatSetting("Opacity", 150f, 0f, 255f);

    private final BehaviorSetting behaviorSetting = new BehaviorSetting("Reload", this::reload);

    public Wallhack() {
        super("Wallhack", "Peep through walls :^)", Category.RENDER);
        setBindKey(Keyboard.KEY_X);

        blocks.add(Blocks.DIAMOND_ORE);
        blocks.add(Blocks.LAPIS_ORE);
        blocks.add(Blocks.GOLD_ORE);
        blocks.add(Blocks.REDSTONE_ORE);
        blocks.add(Blocks.LIT_REDSTONE_ORE);
        blocks.add(Blocks.IRON_ORE);
        blocks.add(Blocks.COAL_ORE);
        blocks.add(Blocks.QUARTZ_ORE);
    }

    @Override
    protected void onEnable() {
        ForgeModContainer.forgeLightPipelineEnabled = false;
        reload();
    }

    @Override
    protected void onDisable() {
        ForgeModContainer.forgeLightPipelineEnabled = true;
        reload();
    }

    private void reload() {
        if (mc.renderGlobal != null) {
            mc.renderGlobal.loadRenderers();
        }
    }
}
