package dev.gavhack.mixin.accessors;

import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface ITimer {

    @Accessor("tickLength")
    float Timer_tickLength_gavhack();
}
