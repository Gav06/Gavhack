package me.gavin.gavhack.command.commands

import me.gavin.gavhack.command.Command
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition

class SpawnTPCommand: Command("spawntp", "Sends NaN position to SpawnTP (for alfheim.pw)", "") {

    override fun execute(command: String, args: Array<String>): Boolean {
        mc.netHandler.addToSendQueue(C04PacketPlayerPosition(Double.NaN, Double.NaN, Double.NaN, Double.NaN, false))
        return true
    }
}