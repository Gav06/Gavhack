package me.gavin.gavhack.gui.api

/* Rect positions and sizes are immutable */
open class Rect(var x: Int, var y: Int, var w: Int, var h: Int) {

    fun isInside(posX: Int, posY: Int) : Boolean {
        return (posX > left()) && (posX < right()) && (posY > top()) && (posY < bottom())
    }

    fun isIntersecting(rectIn: Rect) : Boolean {
        return (left() < rectIn.right()) && (right() > rectIn.left()) && (top() > rectIn.bottom()) && (bottom() < rectIn.top())
    }

    fun setSize(newWidth: Int, newHeight: Int) : Rect {
        this.w = newWidth
        this.h = newHeight
        return this
    }

    fun setPos(newX: Int, newY: Int) : Rect {
        this.x = newX
        this.y = newY
        return this
    }

    fun move(moveX: Int, moveY: Int) : Rect {
        this.x += moveX
        this.y += moveY
        return this
    }

    fun left() : Int {
        return x
    }

    fun right() : Int {
        return x + w
    }

    fun top() : Int {
        return y
    }

    fun bottom() : Int {
        return y + h
    }
}