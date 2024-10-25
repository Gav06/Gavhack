package me.gavin.gavhack.ui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.misc.MathUtils
import me.gavin.gavhack.ui.api.Draggable
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation
import kotlin.math.cos


class WindowHandle(x: Int, y: Int, w: Int, h: Int, val parent: Window): Draggable(x, y, w, h) {

    val categoryIcon = ResourceLocation(parent.category.iconPath)

    init {
        if (parent.closeable) {
            TODO("Implement closable windows later lol")
        }
    }

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        super.onClick(mouseX, mouseY, button)

        if (isInside(mouseX, mouseY) && button == 1) {
            parent.open = !parent.open
            openAnimTime = System.currentTimeMillis()
            ClickGUI.buttonClick()
        }
    }


    // public because it needs to be set from our window handle
    var openAnimTime = 0L
    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {

        val easeIn = getEasingValue()

        if (isInside(mouseX, mouseY)) {
            Gui.drawRect(left(), top(), right(), bottom(), 0x20ffffff)
        }

        Gui.drawRect(left(), bottom() - 1, (left() + (w * easeIn)).toInt(), bottom(), Gavhack.colorScheme.rgb)
        val font = getFont()
        font.drawString(parent.title,
                x + (w / 2) - (font.getStringWidth(parent.title) / 2),
                y + (h / 2) - (font.FONT_HEIGHT / 2), -1)
    }

    fun getEasingValue(): Float {
        val timeMs = (System.currentTimeMillis() - openAnimTime).toFloat()
        val animationProgress = MathUtils.clampf(MathUtils.normalizef(timeMs, 0f, 300f), 0f, 1f)
        val mutatedVal = if (parent.open) animationProgress else 1.0f - animationProgress

        return 1f - cos((mutatedVal * Math.PI) / 2f).toFloat()
    }
}