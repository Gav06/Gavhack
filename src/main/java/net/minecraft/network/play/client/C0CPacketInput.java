package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0CPacketInput extends Packet
{
    public float moveStrafe;
    public float moveForward;
    public boolean jump;
    public boolean sneak;

    public C0CPacketInput() {}

    public C0CPacketInput(float moveStrafing, float moveForward, boolean jump, boolean sneak)
    {
        this.moveStrafe = moveStrafing;
        this.moveForward = moveForward;
        this.jump = jump;
        this.sneak = sneak;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException
    {
        this.moveStrafe = p_148837_1_.readFloat();
        this.moveForward = p_148837_1_.readFloat();
        this.jump = p_148837_1_.readBoolean();
        this.sneak = p_148837_1_.readBoolean();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeFloat(this.moveStrafe);
        buf.writeFloat(this.moveForward);
        buf.writeBoolean(this.jump);
        buf.writeBoolean(this.sneak);
    }

    public void processPacket(INetHandlerPlayServer p_149619_1_)
    {
        p_149619_1_.processInput(this);
    }

    public float func_149620_c()
    {
        return this.moveStrafe;
    }

    public float func_149616_d()
    {
        return this.moveForward;
    }

    public boolean func_149618_e()
    {
        return this.jump;
    }

    public boolean func_149617_f()
    {
        return this.sneak;
    }

    public void processPacket(INetHandler p_148833_1_)
    {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
