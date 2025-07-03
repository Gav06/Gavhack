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
    private int motionX;
    private int motionY;
    private int motionZ;

    public S12PacketEntityVelocity() {}

    public S12PacketEntityVelocity(Entity entity)
    {
        this(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ);
    }

    public S12PacketEntityVelocity(int entityId, double motionX, double motionY, double motionZ)
    {
        this.entityId = entityId;
        double limit = 3.9D;

        if (motionX < -limit)
        {
            motionX = -limit;
        }

        if (motionY < -limit)
        {
            motionY = -limit;
        }

        if (motionZ < -limit)
        {
            motionZ = -limit;
        }

        if (motionX > limit)
        {
            motionX = limit;
        }

        if (motionY > limit)
        {
            motionY = limit;
        }

        if (motionZ > limit)
        {
            motionZ = limit;
        }

        this.motionX = (int)(motionX * 8000.0D);
        this.motionY = (int)(motionY * 8000.0D);
        this.motionZ = (int)(motionZ * 8000.0D);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readInt();
        this.motionX = buf.readShort();
        this.motionY = buf.readShort();
        this.motionZ = buf.readShort();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeInt(this.entityId);
        buf.writeShort(this.motionX);
        buf.writeShort(this.motionY);
        buf.writeShort(this.motionZ);
    }

    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleEntityVelocity(this);
    }

    /**
     * Returns a string formatted as comma separated [field]=[value] values. Used by Minecraft for logging purposes.
     */
    public String serialize()
    {
        return String.format("id=%d, x=%.2f, y=%.2f, z=%.2f", this.entityId, (float) this.motionX / 8000.0F, (float) this.motionY / 8000.0F, (float) this.motionZ / 8000.0F);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public int getMotionX()
    {
        return this.motionX;
    }

    public int getMotionY()
    {
        return this.motionY;
    }

    public int getMotionZ()
    {
        return this.motionZ;
    }

    public void setMotionX(float motionX) {
        this.motionX = (int) (motionX * 8000.0f);
    }

    public void setMotionY(float motionY) {
        this.motionY = (int) (motionY * 8000.0f);
    }

    public void setMotionZ(float motionZ) {
        this.motionZ = (int) (motionZ * 8000.0f);
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
