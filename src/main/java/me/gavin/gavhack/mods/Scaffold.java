package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import me.gavin.gavhack.mods.api.Module;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Scaffold extends Module {

    public Scaffold() {
        super("Scaffold", "Place blocks under you", Category.World);
    }

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        final BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        final BlockPos below = playerPos.down();
        final Block block = mc.world.getBlockState(below).getBlock();
        if (block.isReplaceable(mc.world, below)) {
            // le scaffold
            mc.playerController.processRightClickBlock(mc.player, mc.world, below, EnumFacing.UP, Vec3d.ZERO, EnumHand.MAIN_HAND);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
