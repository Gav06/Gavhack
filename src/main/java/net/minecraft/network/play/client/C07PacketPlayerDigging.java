package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C07PacketPlayerDigging extends Packet
{
    private int x;
    private int y;
    private int z;
    private int side;
    private int action;

    public C07PacketPlayerDigging() {}

    public C07PacketPlayerDigging(int action, int x, int y, int z, int side)
    {
        this.action = action;
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = side;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.action = buf.readUnsignedByte();
        this.x = buf.readInt();
        this.y = buf.readUnsignedByte();
        this.z = buf.readInt();
        this.side = buf.readUnsignedByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeByte(this.action);
        buf.writeInt(this.x);
        buf.writeByte(this.y);
        buf.writeInt(this.z);
        buf.writeByte(this.side);
    }

    public void processPacket(INetHandlerPlayServer handler)
    {
        handler.processPlayerDigging(this);
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getZ()
    {
        return this.z;
    }

    public int getSide()
    {
        return this.side;
    }

    public int getAction()
    {
        return this.action;
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
