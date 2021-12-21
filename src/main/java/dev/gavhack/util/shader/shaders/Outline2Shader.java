package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;

public class Outline2Shader extends FramebufferShader {
    public Outline2Shader() {
        super("/assets/gavhack/shader/outline2.frag", "/assets/gavhack/shader/vertex.vert");
    }

    @Override
    public void setupUniforms() {
        setupUniform("texelSize");
        setupUniform("color");
        setupUniform("filledAlpha");
        setupUniform("width");
    }

    @Override
    public void updateUniforms() {

    }
}
