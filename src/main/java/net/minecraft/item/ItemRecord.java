package net.minecraft.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockJukebox;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRecord extends Item
{
    private static final Map field_150928_b = new HashMap();
    public final String recordName;

    protected ItemRecord(String p_i45350_1_)
    {
        this.recordName = p_i45350_1_;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        field_150928_b.put(p_i45350_1_, this);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        return this.itemIcon;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (world.getBlock(par4, par5, par6) == Blocks.jukebox && world.getBlockMetadata(par4, par5, par6) == 0)
        {
            if (world.isClient)
            {
                return true;
            }
            else
            {
                ((BlockJukebox)Blocks.jukebox).func_149926_b(world, par4, par5, par6, stack);
                world.playAuxSFXAtEntity((EntityPlayer)null, 1005, par4, par5, par6, Item.getIdFromItem(this));
                --stack.stackSize;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(this.getRecordNameLocal());
    }

    public String getRecordNameLocal()
    {
        return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }

    public static ItemRecord func_150926_b(String p_150926_0_)
    {
        return (ItemRecord)field_150928_b.get(p_150926_0_);
    }
}
