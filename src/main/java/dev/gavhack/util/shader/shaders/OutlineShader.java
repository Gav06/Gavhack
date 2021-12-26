package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;
import org.lwjgl.opengl.GL20;

public class OutlineShader extends FramebufferShader {
    public OutlineShader() {
        super("/assets/gavhack/shader/outline.frag", "/assets/gavhack/shader/vertex.vert");
    }

    @Override
    public void setupUniforms() {
        setupUniform("texture");
        setupUniform("texelSize");
        setupUniform("color");
        setupUniform("radius");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform1i(getUniform("texture"), 0);
        GL20.glUniform2f(getUniform("texelSize"), 1F / mc.displayWidth * (radius * quality), 1F / mc.displayHeight * (radius * quality));
        GL20.glUniform4f(getUniform("color"), red, green, blue, alpha);
        GL20.glUniform1f(getUniform("radius"), radius);
    }
}
