package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.AntiFog;
import dev.gavhack.module.mods.Fullbright;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @ModifyVariable(method = "updateLightmap", at = @At("STORE"), index = 16, ordinal = 12)
    public float lightmap(float original) {
        return Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Fullbright.class) ? 24f : original;
    }

    @Inject(method = "setupFog", at = @At("RETURN"))
    public void foggy(int i, float f, CallbackInfo ci) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(AntiFog.class))
            GlStateManager.disableFog();
    }
}
