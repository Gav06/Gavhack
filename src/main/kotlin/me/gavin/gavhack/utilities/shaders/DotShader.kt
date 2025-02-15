package me.gavin.gavhack.utilities.shaders

import me.gavin.gavhack.utilities.GLSLShader
import org.lwjgl.opengl.GL20

class DotShader: GLSLShader(
    "/assets/minecraft/gavhack/shaders/dots.frag",
    "/assets/minecraft/gavhack/shaders/default.vert"
) {

    init {
        setupUniform("texture")
        setupUniform("resolution")
        setupUniform("dotColor")
        setupUniform("fillColor")
    }

    override fun updateUniforms() {
        // set our texture unit to 0
        GL20.glUniform1i(getUniform("texture"), 0)

        GL20.glUniform3f(getUniform("dotColor"), 1f, 1f, 1f)
        GL20.glUniform4f(getUniform("fillColor"), 1f, 0f, 0f, 0.25f)
    }
}