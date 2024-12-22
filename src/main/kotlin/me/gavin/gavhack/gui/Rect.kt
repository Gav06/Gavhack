package me.gavin.gavhack.gui

open class Rect(var x: Int, var y: Int, var w: Int, var h: Int) {

    fun isInside(posX: Int, posY: Int) : Boolean {
        return posX > x && posX < x + w && posY > y && posY < y + h
    }

    fun isOverlapping(other: Rect) : Boolean {
        return x < (other.x + other.w)
                && (x + w) > other.x
                && y > (other.y + other.h)
                && (y + h) < other.y
    }

}