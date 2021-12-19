package dev.gavhack.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gavhack.Gavhack;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private final Minecraft mc = Minecraft.getMinecraft();

    private final File configFolder;
    private final Gson gson;
    private final JsonParser parser;
    private final Set<Configurable> configurables;

    public ConfigManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.parser = new JsonParser();
        this.configFolder = new File(mc.gameDir, Gavhack.MOD_ID);
        this.configurables = new HashSet<>();
        if (!this.configFolder.exists()) {
            this.configFolder.mkdir();
        }
    }

    public void loadConfigSafe() {
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() throws IOException {
        for (Configurable configurable : configurables) {
            final File file = new File(configFolder, configurable.getConfigName() + ".json");
            if (!file.exists())
                continue;

            final FileReader reader = new FileReader(file);
            JsonObject jsonObject = parser.parse(reader).getAsJsonObject();
            configurable.readFromConfig(jsonObject);
            reader.close();
        }
    }

    public void saveConfigSafe() {
        try {
            saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() throws IOException {
        for (Configurable configurable : configurables) {
            final File file = new File(configFolder, configurable.getConfigName() + ".json");
            if (!file.exists()) {
                file.createNewFile();
            }

            final FileWriter writer = new FileWriter(file);
            final JsonObject jsonObject = new JsonObject();
            configurable.writeToConfig(jsonObject);
            gson.toJson(jsonObject, writer);
            writer.flush();
            writer.close();
        }
    }

    public File getConfigFolder() {
        return configFolder;
    }

    public void addConfigurable(Configurable configurable) {
        configurables.add(configurable);
    }
}
