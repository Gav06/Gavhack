package me.gavin.gavhack.mods.api;

import java.util.ArrayList;
import java.util.HashMap;

public final class ModuleManager {

    private final HashMap<String, Module> nameMap = new HashMap<>();
    private final HashMap<Class<? extends Module>, Module> classMap = new HashMap<>();
    private final ArrayList<Module> moduleList = new ArrayList<>();

    public ModuleManager() {
        // register mods


        // sort modules alphabetically
        moduleList.sort(this::sortAlphabetically);
    }

    private void register(Module module) {
        nameMap.put(module.getName(), module);
        classMap.put(module.getClass(), module);
        moduleList.add(module);
    }

    private int sortAlphabetically(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }
}
