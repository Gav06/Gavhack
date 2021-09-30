package me.gavin.gavhack.asm;

import me.gavin.gavhack.asm.patches.EntityPlayerSPPatch;
import me.gavin.gavhack.asm.patches.GuiIngameForgePatch;
import me.gavin.gavhack.asm.patches.MinecraftPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

// this patcher is heavily inspired by seppuku and forgehax
// and the access patching is from MIXIN's accessors
public class PatchManager {

    public static final Logger asmLogger = LogManager.getLogger("Gavhack ASM");
    private final HashMap<String, Patch> patches = new HashMap<>();
    public final boolean isObfuscated;

    public PatchManager(boolean isObfuscated) {
        this.isObfuscated = isObfuscated;

        // register patches here
        register(new MinecraftPatch());
        register(new EntityPlayerSPPatch());
        register(new GuiIngameForgePatch());
    }

    public Patch getPatch(String className) {
        return patches.get(className);
    }

    private void register(Patch patch) {
        patches.put(patch.getClassName().getCurrentName(isObfuscated), patch);
    }

    public Logger getAsmLogger() {
        return asmLogger;
    }
}