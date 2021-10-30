package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Scaffold extends Module {

    public Scaffold() {
        super("Scaffold", "scaffold block", Category.PLAYER);
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        BlockPos below = mc.player.getPosition().down();

        // finding which block to place on
        EnumFacing[] directions = EnumFacing.values();
        for (int i = 0; i < directions.length; i++) {
            BlockPos near = below.offset(directions[i]);
            IBlockState nearState = mc.world.getBlockState(near);
            if (nearState.getBlock().canCollideCheck(nearState, false) && !nearState.getBlock().isReplaceable(mc.world, near)) {
                mc.playerController.processRightClickBlock(mc.player, mc.world, below, directions[i], Vec3d.ZERO, EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
