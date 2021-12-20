package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.NoWeather;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "getRainStrength", at = @At("RETURN"), cancellable = true)
    public void rainHook(float delta, CallbackInfoReturnable<Float> cir) {
        if (Gavhack.isModuleEnabled(NoWeather.class))
            cir.setReturnValue(0f);
    }

    @Inject(method = "getThunderStrength", at = @At("RETURN"), cancellable = true)
    public void thunderHook(float delta, CallbackInfoReturnable<Float> cir) {
        if (Gavhack.isModuleEnabled(NoWeather.class))
            cir.setReturnValue(0f);
    }
}
