package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0CPacketInput extends Packet
{
    public float moveStrafing;
    public float moveForward;
    public boolean jump;
    public boolean sneak;

    public C0CPacketInput() {}

    public C0CPacketInput(float moveStrafing, float moveForward, boolean jump, boolean sneak)
    {
        this.moveStrafing = moveStrafing;
        this.moveForward = moveForward;
        this.jump = jump;
        this.sneak = sneak;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.moveStrafing = buf.readFloat();
        this.moveForward = buf.readFloat();
        this.jump = buf.readBoolean();
        this.sneak = buf.readBoolean();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeFloat(this.moveStrafing);
        buf.writeFloat(this.moveForward);
        buf.writeBoolean(this.jump);
        buf.writeBoolean(this.sneak);
    }

    public void processPacket(INetHandlerPlayServer handler)
    {
        handler.processInput(this);
    }

    public float getMoveStrafe()
    {
        return this.moveStrafing;
    }

    public float getMoveForward()
    {
        return this.moveForward;
    }

    public boolean isJumping()
    {
        return this.jump;
    }

    public boolean isSneaking()
    {
        return this.sneak;
    }

    public void processPacket(INetHandler handler)
    {
        this.processPacket((INetHandlerPlayServer)handler);
    }
}
