package me.gavin.gavhack.event.events

import me.gavin.gavhack.event.Event
import net.minecraft.client.multiplayer.WorldClient

class WorldLoadEvent(val worldClient: WorldClient): Event()