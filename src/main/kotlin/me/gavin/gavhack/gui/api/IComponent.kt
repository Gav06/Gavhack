package me.gavin.gavhack.gui.api

interface IComponent {

    fun onClick(mouseX: Int, mouseY: Int, button: Int)

    fun onRelease(mouseX: Int, mouseY: Int, button: Int)

    fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float)

    fun keyTyped(keyChar: Char, keyCode: Int)
}