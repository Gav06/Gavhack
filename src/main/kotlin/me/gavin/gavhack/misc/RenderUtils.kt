package me.gavin.gavhack.misc

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

object RenderUtils {

    val TEX_TRIANGLE = ResourceLocation("gavhack/triangle.png")


    fun scissorBox(x1: Int, y1: Int, x2: Int, y2: Int, res: ScaledResolution) {
        val scaleFactor = res.scaleFactor
        val displayW = Minecraft.getMinecraft().displayWidth
        val displayH = Minecraft.getMinecraft().displayHeight

        // scissor x and y are the lower left corner
        val scissorX = x1 * scaleFactor
        val scissorY = displayH - (y2 * scaleFactor)

        val scissorWidth = (x2 - x1) * scaleFactor
        val scissorHeight = (displayH - (y2 - y1)) * scaleFactor

        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight)
    }
}