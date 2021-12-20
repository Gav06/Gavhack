package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.mixin.accessors.IMinecraft;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.util.MilliTimer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoFish extends Module {

    private boolean shouldCast = false;
    private boolean shouldReel = false;

    private final MilliTimer timer = new MilliTimer();

    public AutoFish() {
        super("AutoFish", "Automates fishing", Category.PLAYER);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect effect = (SPacketSoundEffect) event.getPacket();
            if (effect.getSound() == SoundEvents.ENTITY_BOBBER_SPLASH) {
                shouldReel = true;
            }
        }
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
            if (shouldReel && timer.hasPassed(500L)) {
                rightClick();
                timer.reset();
                shouldReel = false;
                shouldCast = true;
            } else if (shouldCast && timer.hasPassed(2500L)) {
                rightClick();
                timer.reset();
                shouldCast = false;
            }
        }
    }

    private void rightClick() {
        ((IMinecraft)mc).Minecraft_rightClickMouse();
    }
}