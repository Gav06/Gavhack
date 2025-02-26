package me.gavin.gavhack.utilities.shaders

import me.gavin.gavhack.module.impl.ESP
import me.gavin.gavhack.utilities.GLSLShader
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL20

class OutlineShader: GLSLShader(
    "/assets/minecraft/gavhack/shaders/outline.frag",
    "/assets/minecraft/gavhack/shaders/default.vert"
) {

    init {
        setupUniform("texture")
        setupUniform("resolution")
        setupUniform("radius")

        setupUniform("fill")
        setupUniform("dots")
    }

    override fun updateUniforms() {
        val mc = Minecraft.getMinecraft()
        GL20.glUniform1i(getUniform("texture"), 0)

        GL20.glUniform2f(getUniform("resolution"),
            mc.displayWidth.toFloat(),
            mc.displayHeight.toFloat()
        )

        GL20.glUniform4f(getUniform("color"), 1.0f, 0f, 0f, 1.0f)
        GL20.glUniform1f(getUniform("radius"),1f)

        GL20.glUniform1i(getUniform("fill"), ESP.fill.value.toInt())
        GL20.glUniform1i(getUniform("dots"), ESP.dots.value.toInt())
    }
}

private fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}
