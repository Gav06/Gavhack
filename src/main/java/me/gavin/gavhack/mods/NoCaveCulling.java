package me.gavin.gavhack.mods;

import me.gavin.gavhack.mods.api.Module;

public class NoCaveCulling extends Module {

    public NoCaveCulling() {
        super("NoCaveCulling", "Prevents cave culling", Category.Render);
    }

    @Override
    protected void onEnable() {
        if (mc.renderGlobal != null)
            mc.renderGlobal.loadRenderers();
    }

    @Override
    protected void onDisable() {
        mc.renderGlobal.loadRenderers();
    }
}
