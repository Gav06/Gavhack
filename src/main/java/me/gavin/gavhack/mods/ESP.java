package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;
import net.minecraft.client.shader.Framebuffer;

public class ESP extends Module {

    Framebuffer entityFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
    Framebuffer overlayFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, false);

    public ESP() {
        super("ESP", "Highlights entities", Category.Render);
    }
}
