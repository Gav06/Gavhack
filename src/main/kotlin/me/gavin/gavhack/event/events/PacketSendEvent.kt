package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.network.Packet

class PacketSendEvent(val packet: Packet): Event()