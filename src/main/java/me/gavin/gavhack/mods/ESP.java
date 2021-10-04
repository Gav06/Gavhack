package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.ModeSetting;
import net.minecraft.client.shader.Framebuffer;

public class ESP extends Module {
//
//    Framebuffer entityFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
//    Framebuffer overlayFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, false);

    public final ModeSetting espMode = new ModeSetting("Mode", "Stencil", "Stencil", "Shader", "CSGO");

    public ESP() {
        super("ESP", "Highlights entities", Category.Render);
        this.settings.add(espMode);
    }
}
