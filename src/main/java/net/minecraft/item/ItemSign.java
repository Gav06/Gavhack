package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item
{

    public ItemSign()
    {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            return false;
        }
        else if (!world.getBlock(par4, par5, par6).getMaterial().isSolid())
        {
            return false;
        }
        else
        {
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

            if (!player.canPlayerEdit(par4, par5, par6, par7, stack))
            {
                return false;
            }
            else if (!Blocks.standing_sign.canPlaceBlockAt(world, par4, par5, par6))
            {
                return false;
            }
            else if (world.isClient)
            {
                return true;
            }
            else
            {
                if (par7 == 1)
                {
                    int var11 = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    world.setBlock(par4, par5, par6, Blocks.standing_sign, var11, 3);
                }
                else
                {
                    world.setBlock(par4, par5, par6, Blocks.wall_sign, par7, 3);
                }

                --stack.stackSize;
                TileEntitySign var12 = (TileEntitySign) world.getTileEntity(par4, par5, par6);

                if (var12 != null)
                {
                    player.func_146100_a(var12);
                }

                return true;
            }
        }
    }
}
