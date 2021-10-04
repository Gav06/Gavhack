package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import me.gavin.gavhack.misc.BlockUtil;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.FloatSetting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Lawnmower extends Module {

    private final FloatSetting range = new FloatSetting("Range", 4f, 1f, 6f);
    private final FloatSetting blocksPerTick = new FloatSetting("BlocksPerTick", 2f, 1f, 5f);

    public Lawnmower() {
        super("Lawnmower", "Mows your lawn", Category.World);
        this.settings.add(blocksPerTick);
        this.settings.add(range);
    }

    private int blocksMined = 0;

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        if (shouldRun()) {
            List<BlockPos> posList = BlockUtil.getSurroundingPositions(mc.player, (int) range.getValue(), true).stream()
                    .filter(this::isMowable)
                    .sorted(Comparator.comparing(pos -> mc.player.getDistance(pos.getX(), pos.getY(), pos.getZ())))
                    .collect(Collectors.toList());

            for (BlockPos pos : posList) {
                if (blocksMined < blocksPerTick.getValue()) {
                    mc.playerController.clickBlock(pos, EnumFacing.getDirectionFromEntityLiving(pos, mc.player));
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    blocksMined++;
                }
            }

            blocksMined = 0;
        }
    }

    private boolean isMowable(BlockPos pos) {
        final Block block = mc.world.getBlockState(pos).getBlock();
        return block == Blocks.TALLGRASS || block == Blocks.RED_FLOWER || block == Blocks.YELLOW_FLOWER || block == Blocks.DOUBLE_PLANT;
    }
}
