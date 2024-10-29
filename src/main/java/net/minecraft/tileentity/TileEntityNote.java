package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity
{
    public byte field_145879_a;
    public boolean field_145880_i;
    private static final String __OBFID = "CL_00000362";

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setByte("note", this.field_145879_a);
    }

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        this.field_145879_a = tag.getByte("note");

        if (this.field_145879_a < 0)
        {
            this.field_145879_a = 0;
        }

        if (this.field_145879_a > 24)
        {
            this.field_145879_a = 24;
        }
    }

    public void func_145877_a()
    {
        this.field_145879_a = (byte)((this.field_145879_a + 1) % 25);
        this.onInventoryChanged();
    }

    public void func_145878_a(World p_145878_1_, int p_145878_2_, int p_145878_3_, int p_145878_4_)
    {
        if (p_145878_1_.getBlock(p_145878_2_, p_145878_3_ + 1, p_145878_4_).getMaterial() == Material.air)
        {
            Material var5 = p_145878_1_.getBlock(p_145878_2_, p_145878_3_ - 1, p_145878_4_).getMaterial();
            byte var6 = 0;

            if (var5 == Material.rock)
            {
                var6 = 1;
            }

            if (var5 == Material.sand)
            {
                var6 = 2;
            }

            if (var5 == Material.glass)
            {
                var6 = 3;
            }

            if (var5 == Material.wood)
            {
                var6 = 4;
            }

            p_145878_1_.func_147452_c(p_145878_2_, p_145878_3_, p_145878_4_, Blocks.noteblock, var6, this.field_145879_a);
        }
    }
}
