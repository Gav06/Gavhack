package me.gavin.gavhack.command.impl

import me.gavin.gavhack.command.Command
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.WorldLoadEvent

class FakePlayerCommand: Command("fakeplayer", "Creates a fake player", "<spawn|kill|clear> <name>") {

    private val players = HashMap<String, Int>()

    override fun execute(command: String, args: Array<String>): Boolean {
        if (mc.theWorld == null) {
            error("Fake players can only be used in-game")
            return false
        }

        if (args.size == 1) {
            if (args[0].equals("clear", true)) {
                players.forEach { t, u ->
                    mc.theWorld.removeEntityFromWorld(u)
                }
                players.clear()
                message("Cleared fake players")
            } else if (args[0].equals("spawn", true) || args[0].equals("kill", true)) {
                error("Please specify a name for the fake player to spawn/kill")
                return false
            } else {
                error("Specify if you want to spawn, kill, or clear fake players")
                return false
            }
        } else if (args.size == 2) {
            if (args[0].equals("spawn", true)) {

            } else if (args[0].equals("kill", true)) {

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