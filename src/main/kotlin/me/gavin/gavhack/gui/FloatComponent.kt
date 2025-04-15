package me.gavin.gavhack.gui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.module.setting.FloatSetting
import me.gavin.gavhack.gui.api.GuiComponent
import me.gavin.gavhack.utilities.Utils
import net.minecraft.client.gui.Gui

// Slider!!!
class FloatComponent(val setting: FloatSetting, w: Int, h: Int) : GuiComponent(0, 0, w, h){

    private var draggingSlider = false
    private var sliderWidth = 0

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        if (isInside(mouseX, mouseY) && button == 0) {
            draggingSlider = true
            Utils.clickSound()
        }
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        if (draggingSlider && button == 0) {
            draggingSlider = false
        }
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        updateSlider(mouseX)

        if (isInside(mouseX, mouseY)) {
            Gui.drawRect(left(), top(), right(), bottom(), 0x20ffffff)
        }

        Gui.drawRect(left(), bottom() - 1, left() + sliderWidth, bottom(), Gavhack.colorScheme.rgb)

        val number = if (setting.decimalPlaces == 0) setting.getValue().toInt() else setting.getValue()

        getFont().drawString("${setting.name}: $number",
            x + 2, y + (h / 2) - (getFont().FONT_HEIGHT / 2), -1)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) { }

    private fun updateSlider(mouseX: Int) {
        val diff = MathUtils.clampi(mouseX - x, 0, w)
        val min = setting.minValue
        val max = setting.maxValue
        val normal = MathUtils.normalizef(diff.toFloat(), 0f, w.toFloat())
        sliderWidth = (w * (setting.getValue() - min) / (max - min)).toInt()

        if (draggingSlider) {
            if (diff == 0) {
                setting.setValue(setting.minValue)
            } else {
                val value = ((max - min) * normal) + min
                val round = MathUtils.roundToPlacef(value, 1)
                setting.setValue(round)
            }
        }
    }
}