package net.minecraft.item;

import net.minecraft.block.BlockBed;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBed extends Item
{

    public ItemBed()
    {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (world.isClient)
        {
            return true;
        }
        else if (par7 != 1)
        {
            return false;
        }
        else
        {
            ++par5;
            BlockBed var11 = (BlockBed)Blocks.bed;
            int var12 = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte var13 = 0;
            byte var14 = 0;

            if (var12 == 0)
            {
                var14 = 1;
            }

            if (var12 == 1)
            {
                var13 = -1;
            }

            if (var12 == 2)
            {
                var14 = -1;
            }

            if (var12 == 3)
            {
                var13 = 1;
            }

            if (player.canPlayerEdit(par4, par5, par6, par7, stack) && player.canPlayerEdit(par4 + var13, par5, par6 + var14, par7, stack))
            {
                if (world.isAirBlock(par4, par5, par6) && world.isAirBlock(par4 + var13, par5, par6 + var14) && World.doesBlockHaveSolidTopSurface(world, par4, par5 - 1, par6) && World.doesBlockHaveSolidTopSurface(world, par4 + var13, par5 - 1, par6 + var14))
                {
                    world.setBlock(par4, par5, par6, var11, var12, 3);

                    if (world.getBlock(par4, par5, par6) == var11)
                    {
                        world.setBlock(par4 + var13, par5, par6 + var14, var11, var12 + 8, 3);
                    }

                    --stack.stackSize;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
