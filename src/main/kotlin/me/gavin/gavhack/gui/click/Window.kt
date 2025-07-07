package me.gavin.gavhack.gui.click

import com.google.gson.JsonObject
import me.gavin.gavhack.config.IConfigurable
import me.gavin.gavhack.utilities.RenderUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.gui.api.GuiComponent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.opengl.GL11
import kotlin.math.roundToInt

// Window contains the handle (header) and a list of the subcomponents (the buttons)
class Window(x: Int, y: Int, w: Int, h: Int,
             var title: String,
             val category: ModCategory
): GuiComponent(x, y, w, h), IConfigurable {

    private val defaultHandleHeight = 16

    val handle = WindowHandle(x, y, w, defaultHandleHeight, this)
    val components = ArrayList<WindowButton>()
    var open = true

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        handle.onClick(mouseX, mouseY, button)

        if (open) {
            for (comp in components) {
                comp.onClick(mouseX, mouseY, button)
            }
        }
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        handle.onRelease(mouseX, mouseY, button)

        if (open) {
            for (comp in components) {
                comp.onRelease(mouseX, mouseY, button)
            }
        }
    }

    private var additionalHeight = 0

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        handle.updateDragPosition(mouseX, mouseY)
        this.setPos(handle.x, handle.y)
        this.setSize(this.w, handle.h)


        additionalHeight = 0
        components.forEach {
            additionalHeight += it.getRenderHeight() + 1
        }

        val res = ScaledResolution(Minecraft.getMinecraft())

        val easeValue = handle.getEasingValue()

        // we limit the scissor box to not extend past the height of the screen to prevent weird render bugs
        val scissorBottom = (bottom() + 1 + (additionalHeight * easeValue))
            .coerceAtMost(res.scaledHeight_double.toFloat())

        if (easeValue == 0f)
            additionalHeight = 0


        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        // we have this lovely scissorBox function to handle the maths so the GL_SCISSOR actually fits our
        // view matrix setup
        RenderUtils.scissorBox(left() - 1, top() - 1, right() + 1, scissorBottom.roundToInt(), res)
        Gui.drawRect(
            left() - 1,
            top() - 1,
            right() + 1,
            bottom() + 1 + additionalHeight,
            0xAA000000.toInt()
        )
        handle.onRender(mouseX, mouseY, partialTicks)

        if (open || easeValue != 0f) {
            var yOffset = this.h + 1
            for (comp in components) {
                comp.setPos(this.x, this.y + yOffset)
                comp.onRender(mouseX, mouseY, partialTicks)
                yOffset += comp.getRenderHeight() + 1
            }
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST)
        GL11.glPopMatrix()
    }

    override fun getRenderHeight(): Int {
        return additionalHeight + this.h
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        handle.keyTyped(keyChar, keyCode)
        if (open) {
            components.forEach { it.keyTyped(keyChar, keyCode) }
        }
    }

    override fun getConfigName(): String {
        return title
    }

    override fun getConfigGroup(): String {
        return "windows"
    }

    override fun toJsonObj(): JsonObject {

        val jsonObj = JsonObject()
        jsonObj.addProperty("x", this.handle.x)
        jsonObj.addProperty("y", this.handle.y)
        jsonObj.addProperty("open", this.open)

        println("Writing window: $title to jsonObj: $jsonObj")

        return jsonObj
    }

    override fun fromJsonObj(jsonObj: JsonObject) {

        println("Reading window: $title from jsonObj: $jsonObj")

        this.handle.x = jsonObj.get("x").asInt
        this.handle.y = jsonObj.get("y").asInt
        this.open = jsonObj.get("open").asBoolean
    }
}