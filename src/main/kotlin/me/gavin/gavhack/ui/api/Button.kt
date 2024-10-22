package me.gavin.gavhack.ui.api

/* Basic button class */
class Button(x: Int, y: Int, w: Int, h: Int): Rect(x, y, w, h), IComponent {

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        TODO("Not yet implemented")
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        TODO("Not yet implemented")
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        TODO("Not yet implemented")
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        TODO("Not yet implemented")
    }
}