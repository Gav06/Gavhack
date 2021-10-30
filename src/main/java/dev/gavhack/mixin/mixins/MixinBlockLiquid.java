package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.LiquidInteract;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid {

    @Inject(method = "canCollideCheck", at = @At("HEAD"), cancellable = true)
    public void collide(IBlockState state, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> cir) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(LiquidInteract.class)) {
            cir.setReturnValue(hitIfLiquid && state.getValue(BlockLiquid.LEVEL) == 0);
        }
    }
}
