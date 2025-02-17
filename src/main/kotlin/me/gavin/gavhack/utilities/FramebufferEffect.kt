package me.gavin.gavhack.utilities

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.shader.Framebuffer
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20

class FramebufferEffect(val shader: GLSLShader, var width: Int, var height: Int) {

    val mc = Minecraft.getMinecraft()

    private var effectFramebuffer: Framebuffer = Framebuffer(width, height, true)

    init {
        setupFrameBuffer()
    }

    private fun setupFrameBuffer() {
        effectFramebuffer.deleteFramebuffer()
        effectFramebuffer = Framebuffer(width, height, true)
    }

    fun bindBuffer() {
        GL11.glEnable(GL11.GL_ALPHA_TEST)


        effectFramebuffer.framebufferClear()
        effectFramebuffer.bindFramebuffer(false)
    }

    fun unbindBuffer() {
        effectFramebuffer.unbindFramebuffer()

    }

    fun startShader() {
        GL20.glUseProgram(shader.shaderProgram)
    }

    fun drawQuadWithFramebufferTexture(res: ScaledResolution) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, effectFramebuffer.framebufferTexture)

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

    fun stopShader() {
        GL20.glUseProgram(0);
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
}