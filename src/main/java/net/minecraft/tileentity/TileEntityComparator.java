package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity
{
    private int field_145997_a;
    private static final String __OBFID = "CL_00000349";

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("OutputSignal", this.field_145997_a);
    }

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        this.field_145997_a = tag.getInteger("OutputSignal");
    }

    public int func_145996_a()
    {
        return this.field_145997_a;
    }

    public void func_145995_a(int p_145995_1_)
    {
        this.field_145997_a = p_145995_1_;
    }
}
