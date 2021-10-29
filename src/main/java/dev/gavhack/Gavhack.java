package dev.gavhack;

import dev.gavhack.module.ModuleManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = Gavhack.MOD_ID,
        name = Gavhack.MOD_NAME,
        version = Gavhack.VERSION,
        acceptedMinecraftVersions = "[1.12.2]",
        clientSideOnly = true
)
public class Gavhack {

    public static final String MOD_ID = "gavhack";
    public static final String MOD_NAME = "Gavhack";
    public static final String VERSION = "3.0";

    /** This is the instance of your mod as created by Forge. It will never be null. */
    @Mod.Instance(MOD_ID)
    public static Gavhack INSTANCE;

    private ModuleManager moduleManager;


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.moduleManager = new ModuleManager();
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}