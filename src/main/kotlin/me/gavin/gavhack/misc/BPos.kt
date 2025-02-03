package me.gavin.gavhack.misc

import me.gavin.gavhack.misc.BlockUtil.direction
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.util.Direction
import optifine.BlockPos

class BPos(x: Int, y: Int, z: Int): BlockPos(x, y, z) {

    // returns the BlockPos 1 block further forward and away relative to the player
    fun forward(player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)
        val xOffset = Direction.offsetX[dir]
        val zOffset = Direction.offsetZ[dir]

        return BPos(x + xOffset, y, z + zOffset)
    }

    // BlockPos 1 left of the given position relative to the player
    fun left(player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)

        var xOffset = 0
        var zOffset = 0

        when (dir) {
            // south
            0 -> xOffset = 1
            // west
            1 -> zOffset = 1
            // north
            2 -> xOffset = -1
            // east
            3 -> zOffset = -1
        }

        return BPos(x + xOffset, y, z + zOffset)
    }

    // BlockPos 1 right of the given position relative to the player
    fun right(player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)

        var xOffset = 0
        var zOffset = 0

        when (dir) {
            // south
            0 -> xOffset = -1
            // west
            1 -> zOffset = -1
            // north
            2 -> xOffset = 1
            // east
            3 -> zOffset = 1
        }

        return BPos(x + xOffset, y, z + zOffset)
    }


    // BlockPos behind the given position relative to the player
    fun backwards(player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)

        var xOffset = 0
        var zOffset = 0

        when (dir) {
            // south
            0 -> zOffset = -1
            // west
            1 -> xOffset = -1
            // north
            2 -> zOffset = 1
            // east
            3 -> xOffset = 1
        }

        return BPos(x + xOffset, y, z + zOffset)
    }

    fun up(): BPos {
        return BPos(x, y + 1, z)
    }

    fun down(): BPos {
        return BPos(x, y - 1, z)
    }

    fun north(): BPos {
        return BPos(x, y, z -1)
    }

    fun south(): BPos {
        return BPos(x, y, z + 1)
    }

    fun east(): BPos {
        return BPos(x + 1, y, z)
    }

    fun west(): BPos {
        return BPos(x - 1, y, z)
    }
}