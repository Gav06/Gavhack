package me.gavin.gavhack.ui.api

class Window(x: Int, y: Int, w: Int, h: Int, var title: String, val closeable: Boolean = false): Rect(x, y, w, h), IComponent {

    private val defaultHandleHeight = 12
    private val handle = WindowHandle(x, y, w, defaultHandleHeight, this)

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        handle.onClick(mouseX, mouseY, button)
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        handle.onRelease(mouseX, mouseY, button)
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        handle.onRender(mouseX, mouseY, partialTicks)
        this.setPos(handle.x, handle.y)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        handle.keyTyped(keyChar, keyCode)
    }
}