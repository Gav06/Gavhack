package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity
{
    private final CommandBlockLogic field_145994_a = new CommandBlockLogic()
    {
        private static final String __OBFID = "CL_00000348";
        public ChunkCoordinates getPlayerCoordinates()
        {
            return new ChunkCoordinates(TileEntityCommandBlock.this.x, TileEntityCommandBlock.this.y, TileEntityCommandBlock.this.z);
        }
        public World getEntityWorld()
        {
            return TileEntityCommandBlock.this.getWorldObj();
        }
        public void func_145752_a(String p_145752_1_)
        {
            super.func_145752_a(p_145752_1_);
            TileEntityCommandBlock.this.onInventoryChanged();
        }
        public void func_145756_e()
        {
            TileEntityCommandBlock.this.getWorldObj().func_147471_g(TileEntityCommandBlock.this.x, TileEntityCommandBlock.this.y, TileEntityCommandBlock.this.z);
        }
        public int func_145751_f()
        {
            return 0;
        }
        public void func_145757_a(ByteBuf p_145757_1_)
        {
            p_145757_1_.writeInt(TileEntityCommandBlock.this.x);
            p_145757_1_.writeInt(TileEntityCommandBlock.this.y);
            p_145757_1_.writeInt(TileEntityCommandBlock.this.z);
        }
    };
    private static final String __OBFID = "CL_00000347";

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        this.field_145994_a.func_145758_a(tag);
    }

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        this.field_145994_a.func_145759_b(tag);
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new S35PacketUpdateTileEntity(this.x, this.y, this.z, 2, var1);
    }

    public CommandBlockLogic func_145993_a()
    {
        return this.field_145994_a;
    }
}
