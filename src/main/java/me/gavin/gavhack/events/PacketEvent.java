package me.gavin.gavhack.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public abstract class PacketEvent extends EventCancellable {

    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public static final class Send extends PacketEvent {

        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    public static final class Receive extends PacketEvent {

        public Receive(Packet<?> packet) {
            super(packet);
        }
    }
}
