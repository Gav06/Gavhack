package me.gavin.gavhack.module

import com.google.gson.JsonObject
import me.gavin.gavhack.gui.api.Draggable
import me.gavin.gavhack.gui.api.Rect
import net.minecraft.client.gui.Gui

abstract class HUDModule(name: String, desc: String): Module(name, desc, ModCategory.HUD) {

    // all the sizes and position will be set later
    private var component = Draggable(0, 0, 0, 0)

    fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        updateBounds(component)
        component.updateDragPosition(mouseX, mouseY)
        val color = if (component.isInside(mouseX, mouseY)) 0x80ffffff else 0x80000000
        Gui.drawRect(
            component.x, component.y,
            component.x + component.w,
            component.y + component.h,
            color.toInt()
        )
        drawComponent(component, mouseX, mouseY, partialTicks)
    }

    abstract fun updateBounds(comp: Rect)

    abstract fun drawComponent(comp: Rect, mouseX: Int, mouseY: Int, partialTicks: Float)

    fun getComponent(): Draggable {
        return component
    }

    override fun getConfigGroup(): String {
        return ModCategory.HUD.toString().lowercase()
    }

    override fun toJsonObj(): JsonObject {
        val json = super.toJsonObj()
        json.addProperty("posX", component.x)
        json.addProperty("posY", component.y)

        return json
    }

    override fun fromJsonObj(jsonObj: JsonObject) {
        super.fromJsonObj(jsonObj)

        if (jsonObj.has("posX") && jsonObj.has("posY")) {
            component.x = jsonObj.get("posX").asInt
            component.y = jsonObj.get("posY").asInt
        }
    }
}