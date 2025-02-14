package me.gavin.gavhack.utilities

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class FrameBufferEffect(val shader: GLSLShader, var width: Int, var height: Int) {

    var frameBufferId: Int = -1
    private var colorTextureId: Int = -1

    init {
        setupFrameBuffer()
    }

    private fun setupFrameBuffer() {
        // Generate a new framebuffer object and store its ID
        frameBufferId = GL30.glGenFramebuffers()
        // Make this framebuffer the active one for subsequent operations
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId)

        // Create a texture to store the color output of our rendering
        colorTextureId = GL11.glGenTextures()
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorTextureId)

        // Allocate storage for the texture. NULL at the end means we're not providing any initial data
        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,    // Texture target
            0,                     // Mipmap level
            GL11.GL_RGBA8,         // Internal format (8 bits per channel)
            width,                 // Width
            height,                // Height
            0,                     // Border (must be 0)
            GL11.GL_RGBA,          // Format of pixel data
            GL11.GL_UNSIGNED_BYTE, // Type of pixel data
            null as java.nio.ByteBuffer?  // Pixel data (null = allocate empty texture)
        )

        // Set texture filtering parameters
        // LINEAR means the texture will be smoothly interpolated when scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR)

        // Attach the texture to the framebuffer as its color target
        GL30.glFramebufferTexture2D(
            GL30.GL_FRAMEBUFFER,           // Target
            GL30.GL_COLOR_ATTACHMENT0,     // Attachment point
            GL11.GL_TEXTURE_2D,           // Texture target
            colorTextureId,               // Texture ID
            0                            // Mipmap level
        )

        // Verify the framebuffer is complete and valid
        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            throw RuntimeException("Framebuffer is not complete!")
        }

        // Unbind the framebuffer - return to default framebuffer
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0)
    }

    /**
     * Start rendering to this framebuffer
     * Call this before drawing the content you want to apply the shader to
     */
    fun beginRender() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    /**
     * End rendering to this framebuffer and apply the shader effect
     * This draws the processed content to the screen
     */
    fun endRender() {
        // Switch back to default framebuffer
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0)

        // Activate our shader program
        GL20.glUseProgram(shader.shaderProgram)

        // Bind our framebuffer's texture to texture unit 0
        GL13.glActiveTexture(GL13.GL_TEXTURE0)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorTextureId)

        // Draw a fullscreen quad using normalized device coordinates (-1 to 1)
        // The texture coordinates map our framebuffer texture across this quad
        GL11.glBegin(GL11.GL_QUADS)
        GL11.glTexCoord2f(0f, 0f)  // Bottom-left
        GL11.glVertex2f(-1f, -1f)
        GL11.glTexCoord2f(1f, 0f)  // Bottom-right
        GL11.glVertex2f(1f, -1f)
        GL11.glTexCoord2f(1f, 1f)  // Top-right
        GL11.glVertex2f(1f, 1f)
        GL11.glTexCoord2f(0f, 1f)  // Top-left
        GL11.glVertex2f(-1f, 1f)
        GL11.glEnd()

        // Deactivate shader program
        GL20.glUseProgram(0)
    }

    /**
     * Update framebuffer size when the window is resized
     */
    fun resize(newWidth: Int, newHeight: Int) {
        width = newWidth
        height = newHeight

        // Resize the texture to match new dimensions
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorTextureId)
        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,
            0,
            GL11.GL_RGBA8,
            width,
            height,
            0,
            GL11.GL_RGBA,
            GL11.GL_UNSIGNED_BYTE,
            null as java.nio.ByteBuffer?
        )
    }

    /**
     * Clean up OpenGL resources
     * Call this when the effect is no longer needed
     */
    fun cleanup() {
        GL30.glDeleteFramebuffers(frameBufferId)
        GL11.glDeleteTextures(colorTextureId)
    }
}