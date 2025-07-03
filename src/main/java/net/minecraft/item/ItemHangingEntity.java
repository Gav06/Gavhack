package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item
{
    private final Class hangingEntityClass;

    public ItemHangingEntity(Class p_i45342_1_)
    {
        this.hangingEntityClass = p_i45342_1_;
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
        else if (par7 == 1)
        {
            return false;
        }
        else
        {
            int var11 = Direction.facingToDirection[par7];
            EntityHanging var12 = this.createHangingEntity(world, par4, par5, par6, var11);

            if (!player.canPlayerEdit(par4, par5, par6, par7, stack))
            {
                return false;
            }
            else
            {
                if (var12 != null && var12.onValidSurface())
                {
                    if (!world.isClient)
                    {
                        world.spawnEntityInWorld(var12);
                    }

                    --stack.stackSize;
                }

                return true;
            }
        }
    }

    /**
     * Create the hanging entity associated to this item.
     */
    private EntityHanging createHangingEntity(World par1World, int par2, int par3, int par4, int par5)
    {
        return (EntityHanging)(this.hangingEntityClass == EntityPainting.class ? new EntityPainting(par1World, par2, par3, par4, par5) : (this.hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(par1World, par2, par3, par4, par5) : null));
    }
}
