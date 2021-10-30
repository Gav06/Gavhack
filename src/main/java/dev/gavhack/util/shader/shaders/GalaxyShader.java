package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public class GalaxyShader extends FramebufferShader {
    public static GalaxyShader GALAXY_SHADER;
    public float time;

    public GalaxyShader() {
        super("/assets/gavhack/shader/galaxy.frag", "/assets/gavhack/shader/vertex.vert");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform2f(this.getUniform("resolution"), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight());
        GL20.glUniform1f(this.getUniform("time"), this.time);
        // ???
        this.time += 0.003f;
    }

    static {
        GalaxyShader.GALAXY_SHADER = new GalaxyShader();
    }
}