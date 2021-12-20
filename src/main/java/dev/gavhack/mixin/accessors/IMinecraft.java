package dev.gavhack.mixin.accessors;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface IMinecraft {

    @Accessor("rightClickDelayTimer")
    void Minecraft_rightClickDelayTimer(int value);

    @Invoker("rightClickMouse")
    void Minecraft_rightClickMouse();
}
