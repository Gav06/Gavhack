package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.AntiNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Redirect(method = "dispatchKeypresses", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/GameSettings;setOptionValue(Lnet/minecraft/client/settings/GameSettings$Options;I)V"))
    public void narratorKeyPressRedirect(GameSettings instance, GameSettings.Options settingsOption, int value) {
        if (!Gavhack.isModuleEnabled(AntiNarrator.class)) {
            instance.setOptionValue(settingsOption, value);
        }
    }
}
