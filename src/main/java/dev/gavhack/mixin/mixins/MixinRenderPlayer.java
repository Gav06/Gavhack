package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.events.RenderNametagEvent;
import dev.gavhack.module.mods.NameTags;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer {

    @Inject(method = "renderEntityName(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)V", at = @At("HEAD"), cancellable = true)
    public void nameRender(AbstractClientPlayer player, double posX, double posY, double posZ, String str, double distance, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new RenderNametagEvent(player, posX, posY, posZ, str, distance)))
            ci.cancel();
    }
}
