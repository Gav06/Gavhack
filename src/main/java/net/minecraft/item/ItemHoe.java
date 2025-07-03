package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemHoe extends Item
{
    protected Item.ToolMaterial theToolMaterial;

    public ItemHoe(Item.ToolMaterial p_i45343_1_)
    {
        this.theToolMaterial = p_i45343_1_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45343_1_.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!player.canPlayerEdit(par4, par5, par6, par7, stack))
        {
            return false;
        }
        else
        {
            Block var11 = world.getBlock(par4, par5, par6);

            if (par7 != 0 && world.getBlock(par4, par5 + 1, par6).getMaterial() == Material.air && (var11 == Blocks.grass || var11 == Blocks.dirt))
            {
                Block var12 = Blocks.farmland;
                world.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var12.stepSound.func_150498_e(), (var12.stepSound.func_150497_c() + 1.0F) / 2.0F, var12.stepSound.func_150494_d() * 0.8F);

                if (world.isClient)
                {
                    return true;
                }
                else
                {
                    world.setBlock(par4, par5, par6, var12);
                    stack.damageItem(1, player);
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Returns the name of the material this tool is made from as it is declared in EnumToolMaterial (meaning diamond
     * would return "EMERALD")
     */
    public String getMaterialName()
    {
        return this.theToolMaterial.toString();
    }
}
