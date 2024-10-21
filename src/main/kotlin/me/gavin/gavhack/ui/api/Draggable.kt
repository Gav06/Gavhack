package me.gavin.gavhack.ui.api

abstract class Draggable(x: Int, y: Int, w: Int, h: Int) : Rect(x, y, w, h), IComponent {

    private var dragX: Int = 0
    private var dragY: Int = 0
    private var dragging = false
    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
       if (isInside(mouseX, mouseY)) {
           dragging = true
           dragX = mouseX - x
           dragY = mouseY - y
       }
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        if (dragging) {
            dragging = false
        }
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (dragging) {
            setPos(mouseX - dragX, mouseY - dragY)
        }
    }

    /* Empty declaration because this isn't necessary for all child classes to implement */
    override fun keyTyped(keyChar: Char, keyCode: Int) { }

    fun isDragging() : Boolean {
        return dragging
    }
}