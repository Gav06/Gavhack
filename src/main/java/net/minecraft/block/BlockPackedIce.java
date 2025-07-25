package net.minecraft.block;

import java.util.Random;

import me.gavin.gavhack.module.impl.IceSpeed;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPackedIce extends Block
{

    public BlockPackedIce()
    {
        super(Material.field_151598_x);
        this.slipperiness = 0.98F;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    @Override
    public float getSlipperiness() {
        if (IceSpeed.INSTANCE.isEnabled()) {
            return IceSpeed.INSTANCE.getSpeed().getValue();
        }

        return this.slipperiness;
    }
}
