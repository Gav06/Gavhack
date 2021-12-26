package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public class BlurShader extends FramebufferShader {

    public float blurSize = 0f;
    public float blurQuality = 3f;
    public float blurDirections = 16f;

    public BlurShader() {
        super("/assets/gavhack/shader/blur.frag", "/assets/gavhack/shader/vertex.vert");
    }

    @Override
    public void setupUniforms() {
        setupUniform("texture");
        setupUniform("resolution");
        setupUniform("size");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform1i(getUniform("texture"), 0);
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL20.glUniform2f(getUniform("resolution"), (float)sr.getScaledWidth(), (float)sr.getScaledHeight());
        GL20.glUniform1f(getUniform("size"), blurSize);
    }
}
