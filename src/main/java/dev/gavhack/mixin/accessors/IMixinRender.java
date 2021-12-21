package dev.gavhack.mixin.accessors;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Render.class)
public interface IMixinRender<T extends Entity> {

    @Invoker("bindEntityTexture")
    boolean Render_bindEntityTexture(T t);
}
