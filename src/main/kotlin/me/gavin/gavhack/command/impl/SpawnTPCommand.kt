package me.gavin.gavhack.command.impl

import me.gavin.gavhack.command.Command
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition

class SpawnTPCommand: Command("spawntp", "Sends NaN pos packet (for Alfheim.pw", "") {

    override fun execute(command: String, args: Array<String>): Boolean {
        if (mc.netHandler != null)
            mc.netHandler.addToSendQueue(C04PacketPlayerPosition(Double.NaN, Double.NaN, Double.NaN, Double.NaN, false))

        return true
    }
}