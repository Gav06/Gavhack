package me.gavin.gavhack;

import me.gavin.gavhack.config.ConfigManager;
import me.gavin.gavhack.gui.GavhackGui;
import me.gavin.gavhack.misc.ProjectionUtil;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.mods.api.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

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

    private GavhackGui gavhackGui;

    private ConfigManager configManager;

    /* Instance that will be created by forge */
    @Mod.Instance(ID)
    public static Gavhack INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = LogManager.getLogger(ID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.configManager = new ConfigManager();
        this.moduleManager = new ModuleManager();
        this.gavhackGui = new GavhackGui();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        configManager.loadAll();

        Runtime.getRuntime().addShutdownHook(new Thread(configManager::saveAll));
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (Module module : moduleManager.getModuleList()) {
                if (module.getBind() == Keyboard.getEventKey()) {
                    module.toggle();
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        ProjectionUtil.updateMatrix();
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        fr.drawStringWithShadow(NAME + " " + VERSION, 2f, 2f, 0xff0000);

        float y = 2f;
        for (Module module : moduleManager.getModuleList()) {
            if (module.isEnabled()) {
                final float x = sr.getScaledWidth() - fr.getStringWidth(module.getName()) - 2f;
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

    public GavhackGui getGui() {
        return gavhackGui;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}