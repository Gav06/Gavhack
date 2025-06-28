package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemRedstone extends Item
{

    public ItemRedstone()
    {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (world.getBlock(par4, par5, par6) != Blocks.snow_layer)
        {
            if (par7 == 0)
            {
                --par5;
            }

            if (par7 == 1)
            {
                ++par5;
            }

            if (par7 == 2)
            {
                --par6;
            }

            if (par7 == 3)
            {
                ++par6;
            }

            if (par7 == 4)
            {
                --par4;
            }

            if (par7 == 5)
            {
                ++par4;
            }

            if (!world.isAirBlock(par4, par5, par6))
            {
                return false;
            }
        }

        if (!player.canPlayerEdit(par4, par5, par6, par7, stack))
        {
            return false;
        }
        else
        {
            if (Blocks.redstone_wire.canPlaceBlockAt(world, par4, par5, par6))
            {
                --stack.stackSize;
                world.setBlock(par4, par5, par6, Blocks.redstone_wire);
            }

            return true;
        }
    }
}
