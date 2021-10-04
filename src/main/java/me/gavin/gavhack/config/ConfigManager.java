package me.gavin.gavhack.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.gavin.gavhack.Gavhack;
import me.gavin.gavhack.misc.MC;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager implements MC {

    private final JsonParser parser;
    private final Gson gson;
    private final File gavhackDir;
    private HashMap<String, List<Configurable>> groups;

    public ConfigManager() {
        groups = new HashMap<>();
        gson = new GsonBuilder().setPrettyPrinting().create();
        parser = new JsonParser();

        gavhackDir = new File(mc.gameDir, "gavhack");
        if (!gavhackDir.exists())
            gavhackDir.mkdir();
    }

    public void register(Configurable configurable) {
        if (!groups.containsKey(configurable.getGroup()))
            groups.put(configurable.getGroup(), new ArrayList<>());
        this.groups.get(configurable.getGroup()).add(configurable);
    }

    public void saveAll() {
        System.out.println("saving all");
        groups.forEach((group, configurables) -> saveGroup(group));
    }

    public void loadAll() {
        groups.forEach((group, configurables) -> loadGroup(group));
    }

    public void saveGroup(String group) {
        if (groups.containsKey(group)) {
            System.out.println("saving group " + group);
            for (Configurable configurable : groups.get(group)) {
                try {
                    System.out.println("saving " + configurable.getName());
                    saveConfigurable(configurable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new IllegalStateException("Tried to load nonexistent group");
        }
    }

    public void loadGroup(String group) {
        if (groups.containsKey(group)) {
            for (Configurable configurable : groups.get(group)) {
                try {
                    loadConfigurable(configurable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new IllegalStateException("Tried to load nonexistent group");
        }
    }

    private void saveConfigurable(Configurable configurable) throws Exception {
        final File file = new File(gavhackDir + "/" + configurable.getGroup(),configurable.getName() + ".json");
        if (!file.exists())
            file.createNewFile();

        System.out.println("saving to: " + file.getAbsolutePath());
        final FileWriter writer = new FileWriter(file);
        final JsonObject obj = configurable.read();
        gson.toJson(obj, writer);
        writer.flush();
        writer.close();
    }

    private void loadConfigurable(Configurable configurable) throws Exception {
        final File file = new File(gavhackDir + "/" + configurable.getGroup(),configurable.getName() + ".json");
        if (file.exists()) {
            final FileReader reader = new FileReader(file);
            JsonObject object = (JsonObject) parser.parse(reader);
            configurable.write(object);
            reader.close();
        } else {
            file.mkdir();
            Gavhack.INSTANCE.getLogger().error("Unable to find file: " + file.getAbsolutePath());
        }
    }
}
