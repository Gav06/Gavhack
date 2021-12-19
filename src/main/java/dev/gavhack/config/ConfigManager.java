package dev.gavhack.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import dev.gavhack.Gavhack;
import net.minecraft.client.Minecraft;

import java.io.File;

public class ConfigManager {

    private final Minecraft mc = Minecraft.getMinecraft();

    private String defaultConfig;

    private final File configFolder;
    private final Gson gson;
    private final JsonParser parser;

    public ConfigManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.parser = new JsonParser();
        this.configFolder = new File(mc.gameDir, Gavhack.MOD_ID);
        if (!this.configFolder.exists()) {
            this.configFolder.mkdir();
        }
    }

    public void loadConfig(String configName) {

    }

    public void saveConfig(String configName) {

    }

    public String getDefaultConfig() {
        return defaultConfig;
    }

    public void setDefaultConfig(String name) {
        this.defaultConfig = name;
    }

    public File getConfigFolder() {
        return configFolder;
    }
}
