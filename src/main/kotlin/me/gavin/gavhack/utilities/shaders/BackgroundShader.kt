package me.gavin.gavhack.utilities.shaders

import me.gavin.gavhack.utilities.GLSLShader
import org.lwjgl.opengl.GL20

class BackgroundShader: GLSLShader(
    "/assets/minecraft/gavhack/shaders/background.frag",
    "/assets/minecraft/gavhack/shaders/default.vert"
) {

    init {
        setupUniform("texture")
    }

    override fun updateUniforms() {
        // set our texture unit to 0
        GL20.glUniform1i(getUniform("texture"), 0)
    }
}