package dev.gavhack.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BlockRenderEvent extends Event {

    private final IBlockState state;
    private final IBlockAccess access;
    private final BlockPos pos;

    public BlockRenderEvent(IBlockState state, IBlockAccess access, BlockPos pos) {
        this.state = state;
        this.access = access;
        this.pos = pos;
    }

    public IBlockState getState() {
        return state;
    }

    public IBlockAccess getAccess() {
        return access;
    }

    public BlockPos getPos() {
        return pos;
    }
}
