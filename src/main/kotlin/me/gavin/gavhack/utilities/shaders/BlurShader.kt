package me.gavin.gavhack.utilities.shaders

import me.gavin.gavhack.utilities.GLSLShader
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL20

// This is deprecated because the dual-pass gaussian blur is insanely fucking expensive
// to render, as well as being too complex, but it looks so good...
@Deprecated("slow")
class BlurShader(val type: Type): GLSLShader(
    "/assets/minecraft/gavhack/shaders/${if (type == Type.VERTICAL) "v" else "h"}blur.frag",
    "/assets/minecraft/gavhack/shaders/default.vert"
) {

    init {
        setupUniform("texture")
        setupUniform("texelSize")
        setupUniform("radius")
    }

    override fun updateUniforms() {
        // set our texture unit to 0
        GL20.glUniform1i(getUniform("texture"), 0)
        GL20.glUniform2f(
            getUniform("texelSize"),
            1f / Minecraft.getMinecraft().displayWidth.toFloat(),
            1f / Minecraft.getMinecraft().displayHeight.toFloat()
        )
        GL20.glUniform1i(getUniform("radius"), 20)
    }

    enum class Type {
        VERTICAL,
        HORIZONTAL
    }
}