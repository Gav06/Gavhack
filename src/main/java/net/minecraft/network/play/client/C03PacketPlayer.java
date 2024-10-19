package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer extends Packet
{
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double eyePos;
    protected float field_149476_e;
    protected float field_149473_f;
    protected boolean onGround;
    protected boolean field_149480_h;
    protected boolean field_149481_i;
    private static final String __OBFID = "CL_00001360";

    public C03PacketPlayer() {}

    public C03PacketPlayer(boolean onGround)
    {
        this.onGround = onGround;
    }

    public void processPacket(INetHandlerPlayServer netHandler)
    {
        netHandler.processPlayer(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buffer) throws IOException
    {
        this.onGround = buffer.readUnsignedByte() != 0;
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer packetBuf) throws IOException
    {
        packetBuf.writeByte(this.onGround ? 1 : 0);
    }

    public double func_149464_c()
    {
        return this.posX;
    }

    public double func_149467_d()
    {
        return this.posY;
    }

    public double func_149472_e()
    {
        return this.posZ;
    }

    public double func_149471_f()
    {
        return this.eyePos;
    }

    public float func_149462_g()
    {
        return this.field_149476_e;
    }

    public float func_149470_h()
    {
        return this.field_149473_f;
    }

    public boolean func_149465_i()
    {
        return this.onGround;
    }

    public boolean func_149466_j()
    {
        return this.field_149480_h;
    }

    public boolean func_149463_k()
    {
        return this.field_149481_i;
    }

    public void func_149469_a(boolean p_149469_1_)
    {
        this.field_149480_h = p_149469_1_;
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }

    public static class C04PacketPlayerPosition extends C03PacketPlayer
    {
        private static final String __OBFID = "CL_00001361";

        public C04PacketPlayerPosition()
        {
            this.field_149480_h = true;
        }

        public C04PacketPlayerPosition(double posX, double minY, double eyePos, double posZ, boolean onGround)
        {
            this.posX = posX;
            this.posY = minY;
            this.eyePos = eyePos;
            this.posZ = posZ;
            this.onGround = onGround;
            this.field_149480_h = true;
        }

        public void readPacketData(PacketBuffer buffer) throws IOException
        {
            this.posX = buffer.readDouble();
            this.posY = buffer.readDouble();
            this.eyePos = buffer.readDouble();
            this.posZ = buffer.readDouble();
            super.readPacketData(buffer);
        }

        public void writePacketData(PacketBuffer packetBuf) throws IOException
        {
            packetBuf.writeDouble(this.posX);
            packetBuf.writeDouble(this.posY);
            packetBuf.writeDouble(this.eyePos);
            packetBuf.writeDouble(this.posZ);
            super.writePacketData(packetBuf);
        }

        public void processPacket(INetHandler p_148833_1_)
        {
            super.processPacket((INetHandlerPlayServer)p_148833_1_);
        }
    }

    public static class C06PacketPlayerPosLook extends C03PacketPlayer
    {
        private static final String __OBFID = "CL_00001362";

        public C06PacketPlayerPosLook()
        {
            this.field_149480_h = true;
            this.field_149481_i = true;
        }

        public C06PacketPlayerPosLook(double p_i45254_1_, double p_i45254_3_, double p_i45254_5_, double p_i45254_7_, float p_i45254_9_, float p_i45254_10_, boolean p_i45254_11_)
        {
            this.posX = p_i45254_1_;
            this.posY = p_i45254_3_;
            this.eyePos = p_i45254_5_;
            this.posZ = p_i45254_7_;
            this.field_149476_e = p_i45254_9_;
            this.field_149473_f = p_i45254_10_;
            this.onGround = p_i45254_11_;
            this.field_149481_i = true;
            this.field_149480_h = true;
        }

        public void readPacketData(PacketBuffer buffer) throws IOException
        {
            this.posX = buffer.readDouble();
            this.posY = buffer.readDouble();
            this.eyePos = buffer.readDouble();
            this.posZ = buffer.readDouble();
            this.field_149476_e = buffer.readFloat();
            this.field_149473_f = buffer.readFloat();
            super.readPacketData(buffer);
        }

        public void writePacketData(PacketBuffer packetBuf) throws IOException
        {
            packetBuf.writeDouble(this.posX);
            packetBuf.writeDouble(this.posY);
            packetBuf.writeDouble(this.eyePos);
            packetBuf.writeDouble(this.posZ);
            packetBuf.writeFloat(this.field_149476_e);
            packetBuf.writeFloat(this.field_149473_f);
            super.writePacketData(packetBuf);
        }

        public void processPacket(INetHandler p_148833_1_)
        {
            super.processPacket((INetHandlerPlayServer)p_148833_1_);
        }
    }

    public static class C05PacketPlayerLook extends C03PacketPlayer
    {
        private static final String __OBFID = "CL_00001363";

        public C05PacketPlayerLook()
        {
            this.field_149481_i = true;
        }

        public C05PacketPlayerLook(float p_i45255_1_, float p_i45255_2_, boolean p_i45255_3_)
        {
            this.field_149476_e = p_i45255_1_;
            this.field_149473_f = p_i45255_2_;
            this.onGround = p_i45255_3_;
            this.field_149481_i = true;
        }

        public void readPacketData(PacketBuffer buffer) throws IOException
        {
            this.field_149476_e = buffer.readFloat();
            this.field_149473_f = buffer.readFloat();
            super.readPacketData(buffer);
        }

        public void writePacketData(PacketBuffer packetBuf) throws IOException
        {
            packetBuf.writeFloat(this.field_149476_e);
            packetBuf.writeFloat(this.field_149473_f);
            super.writePacketData(packetBuf);
        }

        public void processPacket(INetHandler p_148833_1_)
        {
            super.processPacket((INetHandlerPlayServer)p_148833_1_);
        }
    }
}
