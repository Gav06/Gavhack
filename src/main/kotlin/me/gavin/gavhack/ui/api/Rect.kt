package me.gavin.gavhack.ui.api

/* Rect positions and sizes are immutable */
class Rect(val x: Int, val y: Int, val w: Int, val h: Int) {

    val left = x
    val right = x + w
    val top = y
    val bottom = y + h

    fun isInside(posX: Int, posY: Int) : Boolean {
        return (posX > left) && (posX < right) && (posY > top) && (posY < bottom)
    }

    fun isIntersecting(rectIn: Rect) : Boolean {
        return (left < rectIn.right) && (right > rectIn.left) && (top > rectIn.bottom) && (bottom < rectIn.top)
    }

    fun resize(newWidth: Int, newHeight: Int) : Rect {
        return Rect(x, y, newWidth, newHeight)
    }

    fun setPos(newX: Int, newY: Int) : Rect {
        return Rect(newX, newY, w, h)
    }

    fun move(moveX: Int, moveY: Int) : Rect {
        return Rect(x + moveX, y + moveY, w, h)
    }
}