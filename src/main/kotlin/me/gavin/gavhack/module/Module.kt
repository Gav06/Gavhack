package me.gavin.gavhack.module

abstract class Module(val name: String, val desc: String?, val category: ModCategory, var bind: Int = 0) {

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
}