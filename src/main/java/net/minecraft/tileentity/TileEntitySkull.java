package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntitySkull extends TileEntity
{
    private int field_145908_a;
    private int field_145910_i;
    private String field_145909_j = "";
    private static final String __OBFID = "CL_00000364";

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setByte("SkullType", (byte)(this.field_145908_a & 255));
        tag.setByte("Rot", (byte)(this.field_145910_i & 255));
        tag.setString("ExtraType", this.field_145909_j);
    }

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        this.field_145908_a = tag.getByte("SkullType");
        this.field_145910_i = tag.getByte("Rot");

        if (tag.func_150297_b("ExtraType", 8))
        {
            this.field_145909_j = tag.getString("ExtraType");
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new S35PacketUpdateTileEntity(this.x, this.y, this.z, 4, var1);
    }

    public void func_145905_a(int p_145905_1_, String p_145905_2_)
    {
        this.field_145908_a = p_145905_1_;
        this.field_145909_j = p_145905_2_;
    }

    public int func_145904_a()
    {
        return this.field_145908_a;
    }

    public int func_145906_b()
    {
        return this.field_145910_i;
    }

    public void func_145903_a(int p_145903_1_)
    {
        this.field_145910_i = p_145903_1_;
    }

    public String func_145907_c()
    {
        return this.field_145909_j;
    }
}
