package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;

public class UnfocusedCPU extends Module {

    public UnfocusedCPU() {
        super("UnfocusedCPU", "Limits render functionality when the window is unfocused", Category.Misc);
    }
}
