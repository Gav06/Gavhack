package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class ItemAxe extends ItemTool
{
    private static final Set field_150917_c = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

    protected ItemAxe(Item.ToolMaterial p_i45327_1_)
    {
        super(3.0F, p_i45327_1_, field_150917_c);
    }

    public float func_150893_a(ItemStack stack, Block block)
    {
        return block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine ? super.func_150893_a(stack, block) : this.efficiencyOnProperMaterial;
    }
}
