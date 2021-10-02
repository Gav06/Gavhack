package me.gavin.gavhack.misc;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhack.Gavhack;
import me.gavin.gavhack.events.KeyPressEvent;
import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import me.gavin.gavhack.events.RenderEvent;
import me.gavin.gavhack.mods.Fullbright;
import me.gavin.gavhack.mods.NoCaveCulling;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import scala.tools.asm.Type;

public final class Hooks {

    public static final String INTERNAL_NAME = Type.getInternalName(Hooks.class);

    public static void EntityPlayerSP$onLivingUpdate(int stage) {
        if (stage == 0) {
            EventManager.call(new PlayerLivingUpdateEvent());
        }
    }

    public static void GuiIngameForge$renderGameOverlay(float partialTicks, ScaledResolution scaledResolution) {
        EventManager.call(new RenderEvent.Screen(partialTicks, scaledResolution));
    }

    public static void Minecraft$runTickKeyboard(int key) {
        EventManager.call(new KeyPressEvent(key));
    }

    public static boolean NetworkManager$sendPacket(Packet<?> packet) {
        final PacketEvent.Send event = new PacketEvent.Send(packet);
        EventManager.call(event);
        return event.isCancelled();
    }

    public static boolean NetworkManager$channelRead0(Packet<?> packet) {
        final PacketEvent.Receive event = new PacketEvent.Receive(packet);
        EventManager.call(event);
        return event.isCancelled();
    }

    public static float EntityRenderer$updateLightmap(float original) {
        return Gavhack.INSTANCE.getModuleManager().getModule(Fullbright.class).isEnabled() ? 24f : original;
    }

    public static boolean VisGraph$setOpaqueCube() {
        return Gavhack.INSTANCE.getModuleManager().getModule(NoCaveCulling.class).isEnabled();
    }
}