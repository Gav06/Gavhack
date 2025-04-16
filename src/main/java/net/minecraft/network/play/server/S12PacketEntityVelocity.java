package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S12PacketEntityVelocity extends Packet
{
    private int entityId;
    private int field_149415_b;
    private int field_149416_c;
    private int field_149414_d;
    private static final String __OBFID = "CL_00001328";

    public S12PacketEntityVelocity() {}

    public S12PacketEntityVelocity(Entity p_i45219_1_)
    {
        this(p_i45219_1_.getEntityId(), p_i45219_1_.motionX, p_i45219_1_.motionY, p_i45219_1_.motionZ);
    }

    public S12PacketEntityVelocity(int entityId, double x, double y, double z)
    {
        this.entityId = entityId;
        double var8 = 3.9D;

        if (x < -var8)
        {
            x = -var8;
        }

        if (y < -var8)
        {
            y = -var8;
        }

        if (z < -var8)
        {
            z = -var8;
        }

        if (x > var8)
        {
            x = var8;
        }

        if (y > var8)
        {
            y = var8;
        }

        if (z > var8)
        {
            z = var8;
        }

        this.field_149415_b = (int)(x * 8000.0D);
        this.field_149416_c = (int)(y * 8000.0D);
        this.field_149414_d = (int)(z * 8000.0D);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException
    {
        this.entityId = p_148837_1_.readInt();
        this.field_149415_b = p_148837_1_.readShort();
        this.field_149416_c = p_148837_1_.readShort();
        this.field_149414_d = p_148837_1_.readShort();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException
    {
        p_148840_1_.writeInt(this.entityId);
        p_148840_1_.writeShort(this.field_149415_b);
        p_148840_1_.writeShort(this.field_149416_c);
        p_148840_1_.writeShort(this.field_149414_d);
    }

    public void processPacket(INetHandlerPlayClient p_149413_1_)
    {
        p_149413_1_.handleEntityVelocity(this);
    }

    /**
     * Returns a string formatted as comma separated [field]=[value] values. Used by Minecraft for logging purposes.
     */
    public String serialize()
    {
        return String.format("id=%d, x=%.2f, y=%.2f, z=%.2f", Integer.valueOf(this.entityId), Float.valueOf((float)this.field_149415_b / 8000.0F), Float.valueOf((float)this.field_149416_c / 8000.0F), Float.valueOf((float)this.field_149414_d / 8000.0F));
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public int func_149411_d()
    {
        return this.field_149415_b;
    }

    public int func_149410_e()
    {
        return this.field_149416_c;
    }

    public int func_149409_f()
    {
        return this.field_149414_d;
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
