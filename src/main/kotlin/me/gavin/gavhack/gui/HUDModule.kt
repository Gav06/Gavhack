package me.gavin.gavhack.gui

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module

abstract class HUDModule(name: String, desc: String): Module(name, desc, ModCategory.HUD) {

    private var componentArea = Rect(0, 0, 0, 0)

    abstract fun drawComponent(mouseX: Int, mouseY: Int, partialTicks: Float, isGui: Boolean)

    fun getComponentArea(): Rect {
        return componentArea
    }

    fun setPos(x: Int, y: Int) {
        componentArea.x = x
        componentArea.y = y
    }

    fun setSize(width: Int, height: Int) {
        this.componentArea.w = width
        this.componentArea.h = height
    }

    fun setArea(x: Int, y: Int, w: Int, h: Int) {
        setArea(Rect(x, y, w, h))
    }

    fun setArea(rect: Rect) {
        this.componentArea = rect
    }

    override fun getConfigGroup(): String {
        return ModCategory.HUD.toString()
    }
}