package me.gavin.gavhack.command.impl

import com.mojang.authlib.GameProfile
import me.gavin.gavhack.command.Command
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.WorldLoadEvent
import net.minecraft.client.entity.EntityOtherPlayerMP

class FakePlayerCommand: Command("fakeplayer", "Creates a fake player", "<spawn|kill|clear> <name>") {

    private val players = HashMap<String, Int>()

    override fun execute(command: String, args: Array<String>): Boolean {
        if (mc.theWorld == null) {
            error("Fake players can only be used in-game")
            return false
        }

        if (args.size == 1) {
            if (args[0].equals("clear", true)) {
                players.forEach { (_, u) ->
                    mc.theWorld.removeEntityFromWorld(u)
                }
                players.clear()
                message("Cleared fake players")
            } else if (args[0].equals("spawn", true) || args[0].equals("kill", true)) {
                error("Please specify a name for the fake player to spawn/kill")
            } else {
                error("Specify if you want to spawn, kill, or clear fake players")
                return false
            }
        } else if (args.size == 2) {
            if (args[0].equals("spawn", true)) {
                if (players.containsKey(args[1])) {
                    error("Cannot have 2 fake players with the same name")
                    return true
                }
                val player = EntityOtherPlayerMP(mc.theWorld, GameProfile("", args[1]))
                player.copyLocationAndAnglesFrom(mc.thePlayer, mc.thePlayer.boundingBox.minY)
                mc.theWorld.addEntityToWorld(-(args[1].hashCode()), player)
                players[player.gameProfile.name] = player.entityId
                message("Spawned fake player named ${player.gameProfile.name}")
            } else if (args[0].equals("kill", true)) {
                if (players.containsKey(args[1])) {
                    mc.theWorld.removeEntityFromWorld(players[args[1]]!!)
                    message("Removed ${args[1]} from world")
                    players.remove(args[1])
                } else {
                    error("No such fake player exists")
                }
            } else {
                error("Please specify to spawn or kill, along with the fake player's name")
                return false
            }
        }

        return true
    }

    private val worldLoadEvent = Listener<WorldLoadEvent> {
        players.clear()
    }

    init {
        EventBus.addListener(worldLoadEvent, WorldLoadEvent::class)
    }
}