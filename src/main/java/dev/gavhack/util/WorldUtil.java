package dev.gavhack.util;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class WorldUtil {

    public static boolean isPosWithinChunk(BlockPos pos, Chunk chunk) {
        return chunk.getBlockState(pos.getX(), pos.getY(), pos.getZ()) != Blocks.AIR.getDefaultState();
    }
}
