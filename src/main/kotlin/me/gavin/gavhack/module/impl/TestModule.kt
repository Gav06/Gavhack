package me.gavin.gavhack.module.impl

import me.gavin.gavhack.misc.Utils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import org.lwjgl.input.Keyboard

class TestModule(name: String) : Module(name, "A module for test purposes", ModCategory.MISC) {

    init {
        enable()
    }
}