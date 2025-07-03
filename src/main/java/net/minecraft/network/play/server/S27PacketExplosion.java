package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;

public class S27PacketExplosion extends Packet
{
    private double field_149158_a;
    private double field_149156_b;
    private double field_149157_c;
    private float field_149154_d;
    private List field_149155_e;
    public float motionX;
    public float motionY;
    public float motionZ;

    public S27PacketExplosion() {}

    public S27PacketExplosion(double x, double y, double z, float size, List p_i45193_8_, Vec3 knockbackVec)
    {
        this.field_149158_a = x;
        this.field_149156_b = y;
        this.field_149157_c = z;
        this.field_149154_d = size;
        this.field_149155_e = new ArrayList(p_i45193_8_);

        if (knockbackVec != null)
        {
            this.motionX = (float)knockbackVec.xCoord;
            this.motionY = (float)knockbackVec.yCoord;
            this.motionZ = (float)knockbackVec.zCoord;
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.field_149158_a = buf.readFloat();
        this.field_149156_b = buf.readFloat();
        this.field_149157_c = buf.readFloat();
        this.field_149154_d = buf.readFloat();
        int var2 = buf.readInt();
        this.field_149155_e = new ArrayList(var2);
        int var3 = (int)this.field_149158_a;
        int var4 = (int)this.field_149156_b;
        int var5 = (int)this.field_149157_c;

        for (int var6 = 0; var6 < var2; ++var6)
        {
            int var7 = buf.readByte() + var3;
            int var8 = buf.readByte() + var4;
            int var9 = buf.readByte() + var5;
            this.field_149155_e.add(new ChunkPosition(var7, var8, var9));
        }

        this.motionX = buf.readFloat();
        this.motionY = buf.readFloat();
        this.motionZ = buf.readFloat();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeFloat((float)this.field_149158_a);
        buf.writeFloat((float)this.field_149156_b);
        buf.writeFloat((float)this.field_149157_c);
        buf.writeFloat(this.field_149154_d);
        buf.writeInt(this.field_149155_e.size());
        int var2 = (int)this.field_149158_a;
        int var3 = (int)this.field_149156_b;
        int var4 = (int)this.field_149157_c;
        Iterator var5 = this.field_149155_e.iterator();

        while (var5.hasNext())
        {
            ChunkPosition var6 = (ChunkPosition)var5.next();
            int var7 = var6.field_151329_a - var2;
            int var8 = var6.field_151327_b - var3;
            int var9 = var6.field_151328_c - var4;
            buf.writeByte(var7);
            buf.writeByte(var8);
            buf.writeByte(var9);
        }

        buf.writeFloat(this.motionX);
        buf.writeFloat(this.motionY);
        buf.writeFloat(this.motionZ);
    }

    public void processPacket(INetHandlerPlayClient p_149151_1_)
    {
        p_149151_1_.handleExplosion(this);
    }

    public float func_149149_c()
    {
        return this.motionX;
    }

    public float func_149144_d()
    {
        return this.motionY;
    }

    public float func_149147_e()
    {
        return this.motionZ;
    }

    public double func_149148_f()
    {
        return this.field_149158_a;
    }

    public double func_149143_g()
    {
        return this.field_149156_b;
    }

    public double func_149145_h()
    {
        return this.field_149157_c;
    }

    public float func_149146_i()
    {
        return this.field_149154_d;
    }

    public List func_149150_j()
    {
        return this.field_149155_e;
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
