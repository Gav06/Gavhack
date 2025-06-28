package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemReed extends Item
{
    private Block field_150935_a;

    public ItemReed(Block p_i45329_1_)
    {
        this.field_150935_a = p_i45329_1_;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        Block var11 = world.getBlock(par4, par5, par6);

        if (var11 == Blocks.snow_layer && (world.getBlockMetadata(par4, par5, par6) & 7) < 1)
        {
            par7 = 1;
        }
        else if (var11 != Blocks.vine && var11 != Blocks.tallgrass && var11 != Blocks.deadbush)
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
        }

        if (!player.canPlayerEdit(par4, par5, par6, par7, stack))
        {
            return false;
        }
        else if (stack.stackSize == 0)
        {
            return false;
        }
        else
        {
            if (world.canPlaceEntityOnSide(this.field_150935_a, par4, par5, par6, false, par7, (Entity)null, stack))
            {
                int var12 = this.field_150935_a.onBlockPlaced(world, par4, par5, par6, par7, par8, par9, par10, 0);

                if (world.setBlock(par4, par5, par6, this.field_150935_a, var12, 3))
                {
                    if (world.getBlock(par4, par5, par6) == this.field_150935_a)
                    {
                        this.field_150935_a.onBlockPlacedBy(world, par4, par5, par6, player, stack);
                        this.field_150935_a.onPostBlockPlaced(world, par4, par5, par6, var12);
                    }

                    world.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), this.field_150935_a.stepSound.func_150496_b(), (this.field_150935_a.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150935_a.stepSound.func_150494_d() * 0.8F);
                    --stack.stackSize;
                }
            }

            return true;
        }
    }
}
