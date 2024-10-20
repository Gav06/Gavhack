package me.gavin.gavhack.module.impl

import me.gavin.gavhack.misc.Utils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import org.lwjgl.input.Keyboard

object TestModule : Module("Test", "A module for test purposes", ModCategory.MISC, Keyboard.KEY_P) {

    override fun onEnable() {
        Utils.gameMessage("test enabled lolol")
        disable()
    }
}