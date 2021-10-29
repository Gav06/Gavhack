package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.GuiMove;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInputFromOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MovementInputFromOptions.class)
public class MixinMovementInputFromOptions {

    @Redirect(method = "updatePlayerMoveState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(KeyBinding keyBinding) {
        int key = keyBinding.getKeyCode();

        if (key > 0 && key < Keyboard.KEYBOARD_SIZE) {
            if (Gavhack.INSTANCE.getModuleManager().isModuleEnabled(GuiMove.class)) {
                if (Minecraft.getMinecraft().currentScreen != null && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
                    if (key != Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())
                        return Keyboard.isKeyDown(key);
                }
            }
        }

        return keyBinding.isKeyDown();
    }
}
