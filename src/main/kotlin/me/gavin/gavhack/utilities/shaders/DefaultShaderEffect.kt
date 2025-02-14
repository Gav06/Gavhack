package me.gavin.gavhack.utilities.shaders

import me.gavin.gavhack.utilities.GLSLShader

class DefaultShaderEffect: GLSLShader(
    "/assets/minecraft/gavhack/shaders/default.frag",
    "/assets/minecraft/gavhack/shaders/default.vert"
)

// no other code needed because no uniforms