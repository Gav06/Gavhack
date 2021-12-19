package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Lawnmower extends Module {

    private final FloatSetting distance = new FloatSetting("Distance", 3.0f, 1.0f, 4.0f);
    private final FloatSetting blocksPerTick = new FloatSetting("BlocksPerTick", 1.0f, 1.0f, 3.0f);

    public Lawnmower() {
        super("Lawnmower", "mows da lawn", Category.PLAYER);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        int blocksMined = 0;
        for (BlockPos pos : MathUtil.getSurroundingBlocks(Math.round(distance.getValue()), true, mc.player)) {
            if (isMowable(mc.world.getBlockState(pos).getBlock()) && blocksMined <= blocksPerTick.getValue()) {
                mc.playerController.clickBlock(pos, EnumFacing.getDirectionFromEntityLiving(pos, mc.player));
                mc.player.swingArm(EnumHand.MAIN_HAND);
                blocksMined++;
            }
        }
    }

    private boolean isMowable(Block block) {
        return block == Blocks.TALLGRASS || block == Blocks.RED_FLOWER || block == Blocks.YELLOW_FLOWER || block == Blocks.DOUBLE_PLANT;
    }
}
