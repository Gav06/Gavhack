package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity
{
    private final MobSpawnerBaseLogic field_145882_a = new MobSpawnerBaseLogic()
    {
        private static final String __OBFID = "CL_00000361";
        public void func_98267_a(int par1)
        {
            TileEntityMobSpawner.this.worldObj.func_147452_c(TileEntityMobSpawner.this.x, TileEntityMobSpawner.this.y, TileEntityMobSpawner.this.z, Blocks.mob_spawner, par1, 0);
        }
        public World getSpawnerWorld()
        {
            return TileEntityMobSpawner.this.worldObj;
        }
        public int getSpawnerX()
        {
            return TileEntityMobSpawner.this.x;
        }
        public int getSpawnerY()
        {
            return TileEntityMobSpawner.this.y;
        }
        public int getSpawnerZ()
        {
            return TileEntityMobSpawner.this.z;
        }
        public void setRandomMinecart(MobSpawnerBaseLogic.WeightedRandomMinecart par1WeightedRandomMinecart)
        {
            super.setRandomMinecart(par1WeightedRandomMinecart);

            if (this.getSpawnerWorld() != null)
            {
                this.getSpawnerWorld().func_147471_g(TileEntityMobSpawner.this.x, TileEntityMobSpawner.this.y, TileEntityMobSpawner.this.z);
            }
        }
    };
    private static final String __OBFID = "CL_00000360";

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        this.field_145882_a.readFromNBT(tag);
    }

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        this.field_145882_a.writeToNBT(tag);
    }

    public void updateEntity()
    {
        this.field_145882_a.updateSpawner();
        super.updateEntity();
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        var1.removeTag("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.x, this.y, this.z, 1, var1);
    }

    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
    {
        return this.field_145882_a.setDelayToMin(p_145842_1_) ? true : super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    public MobSpawnerBaseLogic func_145881_a()
    {
        return this.field_145882_a;
    }
}
