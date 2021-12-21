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
        setupUniform("resolution");
        setupUniform("texture");
        setupUniform("customColor");
        setupUniform("dotCustomColor");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform1i(getUniform("texture"), 0);
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL20.glUniform2f(getUniform("resolution"), (float)sr.getScaledWidth(), (float)sr.getScaledHeight());
        GL20.glUniform4f(getUniform("customColor"), red, green, blue, 0.5f);
        GL20.glUniform3f(getUniform("dotCustomColor"), red, green, blue);
    }
}
