package dev.gavhack.mixin.mixins;

import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.Portals;
import net.minecraft.block.BlockPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockPortal.class)
public class MixinBlockPortal {

    @Unique
    public boolean isCollidable() {
        return !(Gavhack.INSTANCE.getModuleManager().isModuleEnabled(Portals.class) && Portals.hitbox.isEnabled());
    }
}
