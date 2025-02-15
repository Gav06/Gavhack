package me.gavin.gavhack.utilities.shaders

import me.gavin.gavhack.utilities.GLSLShader
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL20

class OutlineShader: GLSLShader(
    "/assets/minecraft/gavhack/shaders/outline.frag",
    "/assets/minecraft/gavhack/shaders/default.vert"
) {

    init {
        setupUniform("texture")
        setupUniform("texelSize")
        setupUniform("color")
        setupUniform("radius")
    }

    override fun updateUniforms() {
        val mc = Minecraft.getMinecraft()
        GL20.glUniform1i(getUniform("texture"), 0)
        GL20.glUniform2f(getUniform("texelSize"), 1F / mc.displayWidth, 1F / mc.displayHeight)
        GL20.glUniform4f(getUniform("color"), 1.0f, 0f, 0f, 1.0f)
        GL20.glUniform1f(getUniform("radius"), 1.0f)
    }
}