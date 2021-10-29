package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.EntityControl;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public class MixinAbstractHorse {

    @Inject(method = "isHorseSaddled", at = @At("HEAD"), cancellable = true)
    public void isHorse(CallbackInfoReturnable<Boolean> cir) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(EntityControl.class)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
