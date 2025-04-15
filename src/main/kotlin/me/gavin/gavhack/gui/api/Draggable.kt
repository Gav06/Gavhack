package me.gavin.gavhack.gui.api

open class Draggable(x: Int, y: Int, w: Int, h: Int) : GuiComponent(x, y, w, h) {

    private var dragX: Int = 0
    private var dragY: Int = 0
    private var dragging = false
    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
       if (isInside(mouseX, mouseY) && button == 0) {
           dragging = true
           dragX = mouseX - x
           dragY = mouseY - y
       }
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        if (dragging && button == 0) {
            dragging = false
        }
    }

    /* Empty declaration because this isn't necessary for all child classes to implement */
    override fun keyTyped(keyChar: Char, keyCode: Int) { }

    fun isDragging() : Boolean {
        return dragging
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) { }

    // Must be called each frame to keep things moving properly
    fun updateDragPosition(mouseX: Int, mouseY: Int) {
        if (dragging) {
            setPos(mouseX - dragX, mouseY - dragY)
        }
    }
}