package me.gavin.gavhack.utilities

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.shader.Framebuffer
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20

class FramebufferEffect(val shader: GLSLShader, var width: Int, var height: Int, val useDepth: Boolean) {

    val mc = Minecraft.getMinecraft()

    private var effectFramebuffer: Framebuffer = Framebuffer(width, height, useDepth)

    init {
        setupFrameBuffer()
    }

    private fun setupFrameBuffer() {
        effectFramebuffer.deleteFramebuffer()
        effectFramebuffer = Framebuffer(width, height, useDepth)
    }

    fun bindBuffer() {
        GL11.glEnable(GL11.GL_ALPHA_TEST)


        effectFramebuffer.framebufferClear()
        effectFramebuffer.bindFramebuffer(false)
    }

    fun unbindBuffer() {
        effectFramebuffer.unbindFramebuffer()

    }

    fun drawFullscreenTexQuad(res: ScaledResolution) {
        drawFullscreenTexQuad(this.effectFramebuffer.framebufferTexture, res)
    }

    fun updateFramebuffer(newWidth: Int, newHeight: Int) {
        if (width == newWidth && height == newHeight) {
            effectFramebuffer.framebufferClear()
            return
        }

        width = newWidth
        height = newHeight

        effectFramebuffer.deleteFramebuffer()
        setupFrameBuffer()
    }

    fun getFrameBuffer(): Framebuffer {
        return effectFramebuffer
    }

    companion object {
        fun drawFullscreenTexQuad(tex: Int, res: ScaledResolution) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0)
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex)

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0.0, 1.0);
            GL11.glVertex2d(0.0, 0.0);
            GL11.glTexCoord2d(0.0, 0.0);
            GL11.glVertex2d(0.0, res.scaledHeight_double);
            GL11.glTexCoord2d(1.0, 0.0);
            GL11.glVertex2d(res.scaledWidth_double, res.scaledHeight_double);
            GL11.glTexCoord2d(1.0, 1.0);
            GL11.glVertex2d(res.scaledWidth_double, 0.0);
            GL11.glEnd();

        }
    }
}