package me.gavin.gavhack.misc

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.Entity
import net.minecraft.util.Direction
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import optifine.BlockPos

object BlockUtil {

    const val BOTTOM_FACE = 0
    const val TOP_FACE = 1
    const val NORTH_FACE = 2
    const val SOUTH_FACE = 3
    const val WEST_FACE = 4
    const val EAST_FAST = 5

    val blockFacingOffsets = arrayOf(
        // bottom
        intArrayOf(0, -1, 0),
        // top
        intArrayOf(0, 1, 0),
        // north
        intArrayOf(0, 0, -1),
        // south
        intArrayOf(0, 0, 1),
        // west
        intArrayOf(-1, 0, 0),
        // east
        intArrayOf(1, 0, 0)
    )

    // taken from GuiIngame.java, used to interface with Direction.java
    fun direction(yaw: Float): Int {
        return MathHelper.floor_double((yaw * 4.0f / 360.0f).toDouble() + 0.5) and 3;
    }

    // returns the BlockPos 1 block further forward and away relative to the player
    fun forward(pos: BlockPos, player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)
        val xOffset = Direction.offsetX[dir]
        val zOffset = Direction.offsetZ[dir]

        return BlockPos(pos.x + xOffset, pos.y, pos.z + zOffset)
    }

    // BlockPos 1 left of the given position relative to the player
    fun left(pos: BlockPos, player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)

        var xOffset: Int = 0
        var zOffset: Int = 0

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

        return BlockPos(pos.x + xOffset, pos.y, pos.z + zOffset)
    }

    // BlockPos 1 right of the given position relative to the player
    fun right(pos: BlockPos, player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)

        var xOffset: Int = 0
        var zOffset: Int = 0

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

        return BlockPos(pos.x + xOffset, pos.y, pos.z + zOffset)
    }


    // BlockPos behind the given position relative to the player
    fun backwards(pos: BlockPos, player: EntityPlayerSP): BlockPos {
        val dir = direction(player.rotationYaw)

        var xOffset: Int = 0
        var zOffset: Int = 0

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

        return BlockPos(pos.x + xOffset, pos.y, pos.z + zOffset)
    }

    fun up(pos: BlockPos, amount: Int): BlockPos {
        return BlockPos(pos.x, pos.y + 1, pos.z)
    }

    fun up(pos: BlockPos): BlockPos {
        return up(pos, 1)
    }


    fun down(pos: BlockPos, amount: Int): BlockPos {
        return BlockPos(pos.x, pos.y - amount, pos.z)
    }

    fun down(pos: BlockPos): BlockPos {
        return down(pos, 1)
    }

    // Uses a given block pos (where you want the block placed), and tries to find a neighboring block to click, returns the side and pos
    fun findValidBlockPlace(posIn: BlockPos, world: World): Placement? {
        for (side in 0..5) {
            val sideOffset = blockFacingOffsets[side]
            val newPos = BlockPos(posIn.x + sideOffset[0], posIn.y + sideOffset[1], posIn.z + sideOffset[2])
            val block = world.getBlock(newPos.x, newPos.y, newPos.z)

            if (!block.material.isReplaceable) {
                return Placement(newPos, getConnectingSide(newPos, posIn))
            }
        }

        // no good side found
        return null
    }

    fun getConnectingSide(posA: BlockPos, posB: BlockPos): Int {
        // 0 - Bottom, 1 - Top, 2 - North, 3 - South, 4 - West, 5 - East
        return when {
            posB.y < posA.y -> 0  // B is below A
            posB.y > posA.y -> 1  // B is above A
            posB.z < posA.z -> 2  // B is north of A
            posB.z > posA.z -> 3  // B is south of A
            posB.x < posA.x -> 4  // B is west of A
            else -> 5            // B is east of A
        }
    }

    fun entityBlockDistance(block: BlockPos, entity: Entity): Double {
        return entity.getDistance(block.x + 0.5, block.y + 0.5, block.z + 0.5)
    }

    data class Placement(val pos: BlockPos, val side: Int) {
        override fun equals(other: Any?): Boolean {
            return if (other is Placement) {
                pos.equals(other.pos) && side == other.side
            } else {
                false
            }
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

}