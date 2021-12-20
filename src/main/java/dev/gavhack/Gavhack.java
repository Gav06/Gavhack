package dev.gavhack;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.gavhack.config.ConfigManager;
import dev.gavhack.gui.GuiDisplayScreen;
import dev.gavhack.module.Module;
import dev.gavhack.module.ModuleManager;
import dev.gavhack.util.ProjectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

    public static final long clientStartTime = System.currentTimeMillis();

    /** This is the instance of your mod as created by Forge. It will never be null. */
    @Mod.Instance(MOD_ID)
    public static Gavhack INSTANCE;

    private ConfigManager configManager;
    private ModuleManager moduleManager;
    private GuiDisplayScreen gui;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.configManager = new ConfigManager();
        this.moduleManager = new ModuleManager();
        this.gui = new GuiDisplayScreen(moduleManager);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.configManager.loadConfigSafe();

        Runtime.getRuntime().addShutdownHook(new Thread(configManager::saveConfigSafe));
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        ProjectionUtil.updateMatrix();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public GuiDisplayScreen getGui() {
        return gui;
    }

    public static boolean isModuleEnabled(Class<? extends Module> clazz) {
        return INSTANCE.getModuleManager().isModuleEnabled(clazz);
    }

    private static final String PREFIX = "[" + ChatFormatting.GREEN + Gavhack.MOD_NAME + ChatFormatting.RESET + "]: ";

    public static void messagePlayer(String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(PREFIX + message));
    }

    public static void messagePlayer(String message, int id) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(PREFIX + message), id);
    }
}