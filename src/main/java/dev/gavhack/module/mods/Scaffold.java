package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Scaffold extends Module {

    public Scaffold() {
        super("Scaffold", "scaffold block", Category.PLAYER);
    }
//
//    private float rotateYaw, rotatePitch;
//
//    @SubscribeEvent
//    public void onUpdate(PlayerUpdateEvent event) {
//        final BlockPos belowPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ).down();
//        final Block belowBlock = getBlock(belowPos);
//
//        if (belowBlock.isReplaceable(mc.world, belowPos)) {
//            float[] angles = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(belowPos.getX() + 0.5, belowPos.getY() + 0.5, belowPos.getZ() + 0.5));
//            mc.player.rotationYaw = angles[0];
//            mc.player.rotationPitch = angles[1];
//            mc.playerController.processRightClickBlock(mc.player, mc.world, belowBlock, EnumFacing.)
//        }
//    }
//
//    private EnumFacing getPlaceFacing(BlockPos pos) {
//        if (!getBlock(pos.north()).isReplaceable(mc.world, pos)) {
//
//        }
//    }
//
//    private Block getBlock(BlockPos pos) {
//        return mc.world.getBlockState(pos).getBlock();
//    }
}
