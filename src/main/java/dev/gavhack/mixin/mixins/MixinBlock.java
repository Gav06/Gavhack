package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.Wallhack;
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "getRenderLayer", at = @At("HEAD"), cancellable = true)
    public void renderLayer(CallbackInfoReturnable<BlockRenderLayer> cir) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Wallhack.class)) {
            cir.setReturnValue(BlockRenderLayer.TRANSLUCENT);
        }
    }
//
//    @Inject(method = "getLightValue*", at = @At("HEAD"), cancellable = true)
//    public void getLightValue(CallbackInfoReturnable<Integer> info) {
//        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Wallhack.class)) {
//            info.setReturnValue(100);
//        }
//    }
}
