package me.gavin.gavhack.gui.click

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.utilities.Utils
import me.gavin.gavhack.gui.api.Draggable
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation


class WindowHandle(x: Int, y: Int, w: Int, h: Int, val parent: Window): Draggable(x, y, w, h) {

    // Maybe use this one day
    val categoryIcon = ResourceLocation(parent.category.iconPath)

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        super.onClick(mouseX, mouseY, button)

        if (isInside(mouseX, mouseY) && button == 1) {
            parent.open = !parent.open
            openAnimTime = System.currentTimeMillis()
            Utils.clickSound()
        }
    }


    // public because it needs to be set from our window handle
    private var openAnimTime = 0L

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

    // This is the value used for animating the open/closing of the gui
    fun getEasingValue(): Float {
        val timeMs = (System.currentTimeMillis() - openAnimTime).toFloat()
        val animationProgress = MathUtils.clampf(MathUtils.normalizef(timeMs, 0f, 300f), 0f, 1f)
        // We do this so it works when opening and closing
        val mutatedVal = if (parent.open) animationProgress else 1.0f - animationProgress

        return Utils.easeInSine(mutatedVal)
    }
}