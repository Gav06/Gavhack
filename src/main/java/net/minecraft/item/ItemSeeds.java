package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemSeeds extends Item
{
    private Block field_150925_a;

    /** BlockID of the block the seeds can be planted on. */
    private Block soilBlockID;

    public ItemSeeds(Block p_i45352_1_, Block p_i45352_2_)
    {
        this.field_150925_a = p_i45352_1_;
        this.soilBlockID = p_i45352_2_;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (player.canPlayerEdit(par4, par5, par6, par7, stack) && player.canPlayerEdit(par4, par5 + 1, par6, par7, stack))
        {
            if (world.getBlock(par4, par5, par6) == this.soilBlockID && world.isAirBlock(par4, par5 + 1, par6))
            {
                world.setBlock(par4, par5 + 1, par6, this.field_150925_a);
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
