package me.gavin.gavhack;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhack.events.KeyPressEvent;
import me.gavin.gavhack.events.RenderEvent;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.mods.api.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        name = Gavhack.NAME,
        modid = Gavhack.ID,
        version = Gavhack.VERSION,
        clientSideOnly = true,
        acceptedMinecraftVersions = "[1.12.2]"
)
public final class Gavhack {

    public static final String NAME = "Gavhack";
    public static final String ID = "gavhack";
    public static final String VERSION = "2.0";

    private Logger logger;

    private ModuleManager moduleManager;

    /* Instance that will be created by forge */
    @Mod.Instance(ID)
    public static Gavhack INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = LogManager.getLogger(ID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.moduleManager = new ModuleManager();

        EventManager.register(this);
    }

    @EventTarget
    public void onKeyPress(KeyPressEvent event) {
        for (Module module : moduleManager.getModuleList()) {
            if (module.getBind() == event.getKey()) {
                module.toggle();
            }
        }
    }

    @EventTarget
    public void onRenderScreen(RenderEvent.Screen event) {
        final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        fr.drawStringWithShadow(NAME + " " + VERSION, 2f, 2f, 0xff0000);

        float y = 2f;
        for (Module module : moduleManager.getModuleList()) {
            if (module.isEnabled()) {
                final float x = event.getScaledResolution().getScaledWidth() - fr.getStringWidth(module.getName()) - 2f;
                fr.drawStringWithShadow(module.getName(), x, y, 0xff0000);
                y += fr.FONT_HEIGHT + 1f;
            }
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}