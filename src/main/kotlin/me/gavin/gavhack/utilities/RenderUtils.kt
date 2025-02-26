package me.gavin.gavhack.utilities

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Vec3
import optifine.BlockPos
import org.lwjgl.opengl.GL11.*

object RenderUtils {

    var shouldCancelShadows = false
    var shouldCancelEnchant = false
    var shouldCancelTileDistanceCheck = false
    var shouldCancelNameTagRender = false

    val TEX_TRIANGLE = ResourceLocation("gavhack/triangle.png")

    // Converts GL scissor to gui overlay coordinates
    fun scissorBox(x1: Int, y1: Int, x2: Int, y2: Int, res: ScaledResolution) {
        val scaleFactor = res.scaleFactor
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

        val a = ((colorIn shr 24) and 0xff) / 255.0f
        val r = ((colorIn shr 16) and 0xff) / 255.0f
        val g = ((colorIn shr 8) and 0xff) / 255.0f
        val b = (colorIn and 0xff) / 255.0f

        if (colorIn != -1) {
            tes.setColorRGBA_F(r, g, b, a)
        }

        tes.startDrawing(GL_LINE_STRIP)
        tes.addVertex(aabb.minX, aabb.minY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.minY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.minY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.minY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.minY, aabb.minZ)
        tes.draw()
        tes.startDrawing(GL_LINE_STRIP)

        if (colorIn != -1) {
            tes.setColorRGBA_F(r, g, b, a)
        }

        tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.maxY, aabb.minZ)
        tes.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.maxY, aabb.maxZ)
        tes.addVertex(aabb.minX, aabb.maxY, aabb.minZ)
        tes.draw()
        tes.startDrawing(GL_LINES)

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

    // The renderManager offset is already baked into this method
    fun renderBlockSideOffset(x: Int, y: Int, z: Int, face: Int, color: Int, renderOffset: Boolean = true) {
        var x1: Double
        var y1: Double
        var z1: Double

        var x2: Double
        var y2: Double
        var z2: Double

        when (face) {
            // bottom
            0 -> {
                x1 = x.toDouble()
                y1 = y.toDouble()
                z1 = z.toDouble()
                x2 = x1 + 1.0
                y2 = y1
                z2 = z1 + 1.0
            }
            // top
            1 -> {
                x1 = x.toDouble()
                y1 = y.toDouble() + 1.0
                z1 = z.toDouble()
                x2 = x1 + 1.0
                y2 = y1
                z2 = z1 + 1.0
            }
            // north (z-)
            2 -> {
                x1 = x.toDouble()
                y1 = y.toDouble()
                z1 = z.toDouble()
                x2 = x1 + 1.0
                y2 = y1 + 1.0
                z2 = z1
            }
            // south (z+)
            3 -> {
                x1 = x.toDouble()
                y1 = y.toDouble()
                z1 = z.toDouble() + 1.0
                x2 = x1 + 1.0
                y2 = y1 + 1.0
                z2 = z1
            }
            // west (x-)
            4 -> {
                x1 = x.toDouble()
                y1 = y.toDouble()
                z1 = z.toDouble()
                x2 = x1
                y2 = y1 + 1.0
                z2 = z1 + 1.0
            }
            // east (x+)
            5 -> {
                x1 = x.toDouble() + 1.0
                y1 = y.toDouble()
                z1 = z.toDouble()
                x2 = x1
                y2 = y1 + 1.0
                z2 = z1 + 1.0
            }
            else -> {
                x1 = x.toDouble()
                y1 = y.toDouble()
                z1 = z.toDouble()
                x2 = x1 + 1.0
                y2 = y1
                z2 = z1 + 1.0
            }
        }

        if (renderOffset) {
            x1 -= RenderManager.renderPosX
            y1 -= RenderManager.renderPosY
            z1 -= RenderManager.renderPosZ

            x2 -= RenderManager.renderPosX
            y2 -= RenderManager.renderPosY
            z2 -= RenderManager.renderPosZ
        }

        val a = (color shr 24 and 255)
        val r = (color shr 16 and 255)
        val g = (color shr 8 and 255)
        val b = (color and 255)

        glDisable(GL_CULL_FACE)
        val tes = Tessellator.instance
        tes.startDrawing(GL_QUADS)
        tes.setColorRGBA(r, g, b, a)

        when (face) {
            // bottom (-Y)
            0 -> {
                tes.addVertex(x1, y1, z1)        // Bottom-Left
                tes.addVertex(x1, y1, z2)        // Top-Left
                tes.addVertex(x2, y1, z2)        // Top-Right
                tes.addVertex(x2, y1, z1)        // Bottom-Right
            }
            // top (+Y)
            1 -> {
                tes.addVertex(x1, y1, z1)        // Bottom-Left
                tes.addVertex(x2, y1, z1)        // Bottom-Right
                tes.addVertex(x2, y1, z2)        // Top-Right
                tes.addVertex(x1, y1, z2)        // Top-Left
            }
            // north (-Z)
            2 -> {
                tes.addVertex(x1, y1, z1)        // Top-Left
                tes.addVertex(x2, y1, z1)        // Top-Right
                tes.addVertex(x2, y2, z1)        // Bottom-Right
                tes.addVertex(x1, y2, z1)        // Bottom-Left
            }
            // south (+Z)
            3 -> {
                tes.addVertex(x2, y1, z2)        // Top-Right
                tes.addVertex(x1, y1, z2)        // Top-Left
                tes.addVertex(x1, y2, z2)        // Bottom-Left
                tes.addVertex(x2, y2, z2)        // Bottom-Right
            }
            // west (-X)
            4 -> {
                tes.addVertex(x1, y1, z2)        // Top-Right
                tes.addVertex(x1, y1, z1)        // Top-Left
                tes.addVertex(x1, y2, z1)        // Bottom-Left
                tes.addVertex(x1, y2, z2)        // Bottom-Right
            }
            // east (+X)
            5 -> {
                tes.addVertex(x2, y1, z1)        // Top-Left
                tes.addVertex(x2, y1, z2)        // Top-Right
                tes.addVertex(x2, y2, z2)        // Bottom-Right
                tes.addVertex(x2, y2, z1)        // Bottom-Left
            }
        }

        tes.draw()

        glEnable(GL_CULL_FACE)
    }

    fun prepare3d() {
        glPushMatrix()
        glDisable(GL_DEPTH_TEST)
        glDisable(GL_TEXTURE_2D)
        glDepthMask(false)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glLineWidth(1.0f)
    }

    fun release3d() {
        glDisable(GL_BLEND)
        glEnable(GL_TEXTURE_2D)
        glEnable(GL_DEPTH_TEST)
        glDepthMask(true)
        glPopMatrix()
    }

    fun getBoxFromBlockPos(pos: BlockPos, renderOffset: Boolean = true): AxisAlignedBB {
        val aabb = AxisAlignedBB.getBoundingBox(
            pos.x.toDouble(),
            pos.y.toDouble(),
            pos.z.toDouble(),
            pos.x + 1.0,
            pos.y + 1.0,
            pos.z + 1.0
        )

        return if (renderOffset)
            aabb.offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ)
        else aabb
    }
}