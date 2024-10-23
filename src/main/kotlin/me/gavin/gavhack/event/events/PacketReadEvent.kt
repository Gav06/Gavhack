package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.network.Packet

class PacketReadEvent(val packet: Packet) : Event()