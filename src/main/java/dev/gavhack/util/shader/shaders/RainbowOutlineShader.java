package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;
import org.lwjgl.opengl.GL20;

public class RainbowOutlineShader extends FramebufferShader {
    public RainbowOutlineShader() {
        super("/assets/gavhack/shader/rainbowOutline.frag", "/assets/gavhack/shader/vertex.vert");
    }

    private float rainbowSpeed = 0.4f;
    private float rainbowStrength = 0.3f;
    private float customSaturation = 0.7f;

    @Override
    public void setupUniforms() {
        setupUniform("texture");
        setupUniform("texelSize");
        setupUniform("color");
        setupUniform("radius");
        setupUniform("rainbowStrength");
        setupUniform("rainbowSpeed");
        setupUniform("saturation");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform1i(getUniform("texture"), 0);
        GL20.glUniform2f(getUniform("texelSize"), 1F / mc.displayWidth * (radius * quality), 1F / mc.displayHeight * (radius * quality));
        GL20.glUniform4f(getUniform("color"), red, green, blue, alpha);
        GL20.glUniform1f(getUniform("radius"), radius);
        float strength = 1.0f / -(1000.0f * this.rainbowStrength);
        GL20.glUniform2f(getUniform("rainbowStrength"), strength, strength);
        GL20.glUniform1f(getUniform("rainbowSpeed"), rainbowSpeed);
        GL20.glUniform1f(getUniform("saturation"), customSaturation);
    }
}
