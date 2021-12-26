package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public class DotShader extends FramebufferShader {
    public DotShader() {
        super("/assets/gavhack/shader/dots.frag", "/assets/gavhack/shader/vertex.vert");
    }

    @Override
    public void setupUniforms() {
        setupUniform("texture");
        setupUniform("resolution");
        setupUniform("dotColor");
        setupUniform("fillColor");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform1f(getUniform("texture"), 0);
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL20.glUniform2f(getUniform("resolution"), (float)sr.getScaledWidth(), (float)sr.getScaledHeight());
        GL20.glUniform3f(getUniform("dotColor"), red, green, blue);
        GL20.glUniform4f(getUniform("fillColor"), red, green, blue, alpha);
    }
}