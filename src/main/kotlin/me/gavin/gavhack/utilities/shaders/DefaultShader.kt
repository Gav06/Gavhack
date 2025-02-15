package me.gavin.gavhack.utilities.shaders

import me.gavin.gavhack.utilities.GLSLShader
import org.lwjgl.opengl.GL20

class DefaultShader: GLSLShader(
    "/assets/minecraft/gavhack/shaders/default.frag",
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

// no other code needed because no uniforms