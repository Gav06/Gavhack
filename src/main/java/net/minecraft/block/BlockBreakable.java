package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

public class BlockBreakable extends Block
{
    private boolean field_149996_a;
    private String field_149995_b;
    private static final String __OBFID = "CL_00000254";

    protected BlockBreakable(String p_i45411_1_, Material p_i45411_2_, boolean p_i45411_3_)
    {
        super(p_i45411_2_);
        this.field_149996_a = p_i45411_3_;
        this.field_149995_b = p_i45411_1_;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        Block var6 = blockAccess.getBlock(x, y, z);

        if (this == Blocks.glass || this == Blocks.stained_glass)
        {
            if (blockAccess.getBlockMetadata(x, y, z) != blockAccess.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]))
            {
                return true;
            }

            if (var6 == this)
            {
                return false;
            }
        }

        return !this.field_149996_a && var6 == this ? false : super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.field_149995_b);
    }
}
