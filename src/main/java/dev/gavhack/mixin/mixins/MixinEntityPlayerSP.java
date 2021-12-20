package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.mods.NoSwing;
import dev.gavhack.module.mods.PortalTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    public void onLivingUpdateMixin(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PlayerUpdateEvent());
    }

    @Redirect(method = "onLivingUpdate",  at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void displayScreen(Minecraft instance, GuiScreen i) {
        if (!(Gavhack.INSTANCE.getModuleManager().isModuleEnabled(PortalTweaks.class) && PortalTweaks.chat.isEnabled())) {
            instance.displayGuiScreen(i);
        }
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void close(EntityPlayerSP instance) {
        if (!(Gavhack.INSTANCE.getModuleManager().isModuleEnabled(PortalTweaks.class) && PortalTweaks.chat.isEnabled())) {
            instance.closeScreen();
        }
    }

    @Inject(method = "swingArm", at = @At("HEAD"), cancellable = true)
    public void swing(EnumHand hand, CallbackInfo ci) {
        if (Gavhack.isModuleEnabled(NoSwing.class) && NoSwing.swingMode.getValue() == NoSwing.SwingMode.All)
            ci.cancel();
    }
}
