package me.gavin.gavhack.module

import net.minecraft.client.Minecraft

abstract class Module(val name: String, val desc: String?, val category: ModCategory) {

    protected val mc = Minecraft.getMinecraft()

    private var enabled = false

    protected open fun onEnable() { }

    protected open fun onDisable() { }

    fun enable() {
        enabled = true
        onEnable()
    }

    fun disable() {
        enabled = false
        onDisable()
    }

    fun toggle() {
        if (enabled) disable() else enable()
    }

    fun isEnabled() : Boolean {
        return enabled
    }
}