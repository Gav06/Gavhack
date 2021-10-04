package me.gavin.gavhack.misc;

import me.gavin.gavhack.Gavhack;
import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import me.gavin.gavhack.mods.Fullbright;
import me.gavin.gavhack.mods.NoCaveCulling;
import me.gavin.gavhack.mods.Timer;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import scala.tools.asm.Type;

public final class Hooks implements MC {

    public static final String INTERNAL_NAME = Type.getInternalName(Hooks.class);

    public static void EntityPlayerSP$onLivingUpdate(int stage) {
        if (stage == 0) {
            MinecraftForge.EVENT_BUS.post(new PlayerLivingUpdateEvent());
        }
    }

    public static boolean NetworkManager$sendPacket(Packet<?> packet) {
        return MinecraftForge.EVENT_BUS.post(new PacketEvent.Send(packet));
    }

    public static boolean NetworkManager$channelRead0(Packet<?> packet) {
        return MinecraftForge.EVENT_BUS.post(new PacketEvent.Receive(packet));
    }

    public static float EntityRenderer$updateLightmap(float original) {
        return Gavhack.INSTANCE.getModuleManager().getModule(Fullbright.class).isEnabled() ? 24f : original;
    }

    public static boolean VisGraph$setOpaqueCube() {
        return Gavhack.INSTANCE.getModuleManager().getModule(NoCaveCulling.class).isEnabled();
    }

    public static float Timer$updateTimer() {
        final Timer timer = Gavhack.INSTANCE.getModuleManager().getModule(Timer.class);
        return timer.isEnabled() ? timer.speed.getValue() : 1.0f;
    }
}