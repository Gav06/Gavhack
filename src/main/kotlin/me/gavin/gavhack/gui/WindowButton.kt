package me.gavin.gavhack.gui

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.utilities.Utils
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.gui.api.GuiComponent
import net.minecraft.client.gui.Gui
import kotlin.math.roundToInt

class WindowButton(val module: Module, x: Int, y: Int, w: Int, h: Int): GuiComponent(x, y, w, h) {

    var open = false
    val subComponents = ArrayList<GuiComponent>()

    private var openAnimTime = 0L

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        if (isInside(mouseX, mouseY)) {
            when (button) {
                0 -> {
                    module.toggle()
                    ClickGUI.buttonClick()
                }
                1 -> {
                    this.open = !open
                    openAnimTime = System.currentTimeMillis()
                    ClickGUI.buttonClick()
                }
            }
        }

        if (open) {
            subComponents.forEach { it.onClick(mouseX, mouseY, button) }
        }
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        if (open) {
            subComponents.forEach { it.onRelease(mouseX, mouseY, button) }
        }
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Gui.drawRect(left(), top(), right(), bottom(), 0x50000000)

        if (isInside(mouseX, mouseY)) {
            Gui.drawRect(left(), top(), right(), bottom(), 0x20ffffff)
        }

        val color = if (module.isEnabled()) Gavhack.colorScheme.rgb else -1
        getFont().drawString(module.name,
            x + (w / 2) - (getFont().getStringWidth(module.name) / 2),
            y + (h / 2) - (getFont().FONT_HEIGHT / 2), color)




        if (open && getEasingValue() == 1f) {
            /*
                // save previous scissor box
                val res = ScaledResolution(
                Minecraft.getMinecraft().gameSettings,
                Minecraft.getMinecraft().displayWidth,
                Minecraft.getMinecraft().displayHeight
                )

                val intBuf = BufferUtils.createIntBuffer(16)
                val on = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST)
                GL11.glGetInteger(GL11.GL_SCISSOR_BOX, intBuf)

                GL11.glEnable(GL11.GL_SCISSOR_TEST)
                RenderUtils.scissorBox(
                    left(),
                    top(),
                    right(),
                    bottom() + (getComponentsHeight() * getEasingValue()).roundToInt(),
                    res
                )
             */

            var yOffset = 1
            for (comp in subComponents) {
                comp.setPos(this.x, this.bottom() + yOffset)
                comp.onRender(mouseX, mouseY, partialTicks)

                yOffset += comp.getRenderHeight() + 1
            }

            /*
                // restore our previous scissor box (openGL scissor boxes are a pain to do multiple)
                GL11.glScissor(intBuf.get(), intBuf.get(), intBuf.get(), intBuf.get())

                if (!on)
                    GL11.glDisable(GL11.GL_SCISSOR_TEST)
             */
        }
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        if (open) {
            subComponents.forEach { it.keyTyped(keyChar, keyCode) }
        }
    }

    // This private because we only need it here.
    // Also, it's basically the same as the one from the handle
    // I promise ill clean up this animation stuff one day
    private fun getEasingValue(): Float {
        val timeMs = (System.currentTimeMillis() - openAnimTime).toFloat()
        // 150f because our anim is 150 ms in length
        val animationProgress = MathUtils.clampf(MathUtils.normalizef(timeMs, 0f, 150f), 0f, 1f)
        val mutatedVal = if (this.open) animationProgress else 1.0f - animationProgress

        return Utils.easeInSine(mutatedVal)
    }

    override fun getRenderHeight(): Int {
        return if (open || getEasingValue() != 0f) {
            return (getComponentsHeight() * this.getEasingValue()).roundToInt() + this.h
        } else {
            this.h
        }
    }

    private fun getComponentsHeight(): Int {
        var h = 0
        for (comp in subComponents) {
            h += 1 + comp.getRenderHeight()
        }
        return h
    }
}