package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public class SmoothOutlineShader extends FramebufferShader {
    public SmoothOutlineShader() {
        super("/assets/gavhack/shader/smoothOutline.frag", "/assets/gavhack/shader/vertex.vert");
    }

    @Override
    public void setupUniforms() {
        setupUniform("texture");
        setupUniform("resolution");
        setupUniform("color");
        setupUniform("size");
        setupUniform("divider");
        setupUniform("outerAlpha");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform1i(getUniform("texture"), 0);
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL20.glUniform2f(getUniform("resolution"), (float)sr.getScaledWidth(), (float)sr.getScaledHeight());
        GL20.glUniform4f(getUniform("color"), red, green, blue, alpha);
        GL20.glUniform1f(getUniform("outerAlpha"), 0.01f);
        GL20.glUniform1f(getUniform("divider"), quality);
        GL20.glUniform1f(getUniform("size"), radius);
    }
}
