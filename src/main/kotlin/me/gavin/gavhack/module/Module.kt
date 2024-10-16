package me.gavin.gavhack.module

abstract class Module(val name: String, val desc: String?, val category: ModCategory, var bind: Int = 0) {

    fun toggle() {

    }
}