package me.gavin.gavhack.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {

    public static List<BlockPos> getSurroundingPositions(EntityPlayer player, int range, boolean addMotion) {

        Vec3d playerVec = player.getPositionVector();
        if (addMotion) {
            playerVec = playerVec.add(player.motionX, player.motionY, player.motionZ);
        }
        final List<BlockPos> list = new ArrayList<>();

        for (int x = -range; x < range; x++) {
            for (int y = -range; y < range; y++) {
                for (int z = -range; z < range; z++) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    list.add(pos.add(playerVec.x, playerVec.y, playerVec.z));
                }
            }
        }

        return list;
    }
}
