package me.gavin.gavhack.misc;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import me.gavin.gavhack.events.RenderEvent;
import net.minecraft.client.gui.ScaledResolution;
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
}
