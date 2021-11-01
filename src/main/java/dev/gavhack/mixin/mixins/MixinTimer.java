package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.mixin.accessors.ITimer;
import dev.gavhack.module.mods.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.util.Timer.class)
public class MixinTimer {

    @Redirect(method = "updateTimer", at = @At(value = "FIELD", target = "Lnet/minecraft/util/Timer;tickLength:F"))
    public float fieldRedirect(net.minecraft.util.Timer timer) {
        final float tickLength = ((ITimer)timer).Timer_tickLength_gavhack();
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Timer.class)) {
            return tickLength / Timer.speed.getValue();
        } else {
            return tickLength;
        }
    }
}
