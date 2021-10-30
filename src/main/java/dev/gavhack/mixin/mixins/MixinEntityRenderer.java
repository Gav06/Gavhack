package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.Fullbright;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @ModifyVariable(method = "updateLightmap", at = @At("STORE"), index = 16, ordinal = 12)
    public float lightmap(float original) {
        return Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Fullbright.class) ? 24f : original;
    }
}
