package dev.gavhack.util.shader.shaders;

import dev.gavhack.util.shader.FramebufferShader;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;

import java.nio.FloatBuffer;

public class OutlineShader extends FramebufferShader {
    public OutlineShader() {
        super("/assets/gavhack/shader/outline.frag", "/assets/gavhack/shader/vertex.vert");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("TexelSize");
        this.setupUniform("DiffuseSampler");
    }

    @Override
    public void updateUniforms() {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final FloatBuffer texelSizeBuffer = BufferUtils.createFloatBuffer(2);
        texelSizeBuffer.position(0);
        texelSizeBuffer.put(1.0F / scaledResolution.getScaledWidth());
        texelSizeBuffer.put(1.0F / scaledResolution.getScaledHeight());
        texelSizeBuffer.flip();
        ARBShaderObjects.glUniform2ARB(getUniform("TexelSize"), texelSizeBuffer);
        ARBShaderObjects.glUniform1iARB(getUniform("DiffuseSampler"), 0);
    }
}
