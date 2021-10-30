package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.Wallhack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    public void shouldRenderSide(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Wallhack.class)) {
            if (Wallhack.blocks.contains(blockState.getBlock()))
                cir.setReturnValue(true);
        }
    }

    @Redirect(method = "canRenderInLayer", at = @At(value = "INVOKE", target = "net/minecraft/block/Block.getRenderLayer()Lnet/minecraft/util/BlockRenderLayer;"))
    public BlockRenderLayer canRender(Block instance) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Wallhack.class)) {
            if (Wallhack.blocks.contains(instance)) {
                return BlockRenderLayer.SOLID;
            } else {
                return BlockRenderLayer.TRANSLUCENT;
            }
        } else {
            return instance.getRenderLayer();
        }
    }

//    @Inject(method = "getLightValue(Lnet/minecraft/block/state/IBlockState;)I", at = @At("HEAD"), cancellable = true)
//    public void getLight1(IBlockState state, CallbackInfoReturnable<Integer> cir) {
//        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Wallhack.class)) {
//            if (Wallhack.blocks.contains(state.getBlock()))
//                cir.setReturnValue(15);
//        }
//    }
//
//    @Inject(method = "getLightValue(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)I", at = @At("HEAD"), cancellable = true, remap = false)
//    public void getLight(IBlockState state, IBlockAccess access, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
//        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Wallhack.class)) {
//            if (Wallhack.blocks.contains(state.getBlock()))
//            cir.setReturnValue(15);
//        }
//    }

    @Inject(method = "isFullCube", at = @At("HEAD"), cancellable = true)
    public void isCube(IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Wallhack.class))
            cir.setReturnValue(Wallhack.blocks.contains(state.getBlock()));
    }

    @Inject(method = "addCollisionBoxToList(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/util/math/AxisAlignedBB;)V", at = @At("HEAD"), cancellable = true)
    private static void box(BlockPos axisalignedbb, AxisAlignedBB pos, List<AxisAlignedBB> entityBox, AxisAlignedBB collidingBoxes, CallbackInfo ci) {

    }
}
