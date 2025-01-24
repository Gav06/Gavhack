package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C08PacketPlayerBlockPlacement extends Packet
{
    private int x;
    private int y;
    private int z;
    private int side;
    private ItemStack itemStack;
    private float facingX;
    private float facingY;
    private float facingZ;

    public C08PacketPlayerBlockPlacement() {}

    public C08PacketPlayerBlockPlacement(int x, int y, int z, int side, ItemStack itemStack, float facingX, float facingY, float facingZ)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = side;
        this.itemStack = itemStack != null ? itemStack.copy() : null;
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer packetBuffer) throws IOException
    {
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readUnsignedByte();
        this.z = packetBuffer.readInt();
        this.side = packetBuffer.readUnsignedByte();
        this.itemStack = packetBuffer.readItemStackFromBuffer();
        this.facingX = (float)packetBuffer.readUnsignedByte() / 16.0F;
        this.facingY = (float)packetBuffer.readUnsignedByte() / 16.0F;
        this.facingZ = (float)packetBuffer.readUnsignedByte() / 16.0F;
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer packetBuffer) throws IOException
    {
        packetBuffer.writeInt(this.x);
        packetBuffer.writeByte(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.side);
        packetBuffer.writeItemStackToBuffer(this.itemStack);
        packetBuffer.writeByte((int)(this.facingX * 16.0F));
        packetBuffer.writeByte((int)(this.facingY * 16.0F));
        packetBuffer.writeByte((int)(this.facingZ * 16.0F));
    }

    public void processPacket(INetHandlerPlayServer p_149572_1_)
    {
        p_149572_1_.processPlayerBlockPlacement(this);
    }

    public int func_149576_c()
    {
        return this.x;
    }

    public int func_149571_d()
    {
        return this.y;
    }

    public int func_149570_e()
    {
        return this.z;
    }

    public int func_149568_f()
    {
        return this.side;
    }

    public ItemStack func_149574_g()
    {
        return this.itemStack;
    }

    public float func_149573_h()
    {
        return this.facingX;
    }

    public float func_149569_i()
    {
        return this.facingY;
    }

    public float func_149575_j()
    {
        return this.facingZ;
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
