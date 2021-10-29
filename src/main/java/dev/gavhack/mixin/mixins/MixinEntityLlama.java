package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.EntityControl;
import net.minecraft.entity.passive.EntityLlama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLlama.class)
public class MixinEntityLlama {

    @Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
    public void canSteer(CallbackInfoReturnable<Boolean> cir) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(EntityControl.class)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
