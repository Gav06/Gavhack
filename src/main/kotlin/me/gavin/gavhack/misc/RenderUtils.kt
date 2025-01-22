package me.gavin.gavhack.misc

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11.*

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

        glScissor(scissorX, scissorY, scissorWidth, scissorHeight)
    }

    fun outlineRect(x1: Float, y1: Float, x2: Float, y2: Float, color: Int) {
        val r = (color shr 16 and 255).toFloat() / 255.0f
        val g = (color shr 8 and 255).toFloat() / 255.0f
        val b = (color and 255).toFloat() / 255.0f
        val a = (color shr 24 and 255).toFloat() / 255.0f


        glDisable(GL_TEXTURE_2D)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glColor4f(r, g, b, a)
        val t = Tessellator.instance

        t.startDrawing(GL_LINE_LOOP)
        t.addVertex(x1.toDouble(), y2.toDouble(), 0.0)
        t.addVertex(x1.toDouble(), y1.toDouble(), 0.0)
        t.addVertex(x2.toDouble(), y1.toDouble(), 0.0)
        t.addVertex(x2.toDouble(), y2.toDouble(), 0.0)
        t.draw()

        glDisable(GL_BLEND)
        glEnable(GL_TEXTURE_2D)
    }

    fun getRenderPos(): Vec3 {
        return Vec3.createVectorHelper(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ)
    }

    // just minecraft's render method modified to accept alpha color values too
    fun drawOutlinedBoundingBox(aabb: AxisAlignedBB, colorIn: Int) {
        val tes = Tessellator.instance
        tes.startDrawing(3)

        val a = ((colorIn shr 24) and 0xff) / 255.0f
        val r = ((colorIn shr 16) and 0xff) / 255.0f
        val g = ((colorIn shr 8) and 0xff) / 255.0f
        val b = (colorIn and 0xff) / 255.0f

        if (colorIn != -1) {
            tes.setColorRGBA_F(r, g, b, a)
        }

        tes.addVertex(aabb.minX, aabb.minY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.minY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.minY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.minY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.minY, aabb.minZ)
        tes.draw()
        tes.startDrawing(3)

        if (colorIn != -1) {
            tes.setColorRGBA_F(r, g, b, a)
        }

        tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.maxY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.maxY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ)
        tes.draw()
        tes.startDrawing(1)

        if (colorIn != -1) {
            tes.setColorRGBA_F(r, g, b, a)
        }

        tes.addVertex(aabb.minX, aabb.minY, aabb.minZ)
        tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.minY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.maxY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.minY, aabb.maxZ)
        tes.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.minY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.maxY, aabb.maxZ)
        tes.draw()
    }
}