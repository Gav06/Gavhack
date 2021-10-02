package me.gavin.gavhack.mods.api;

import me.gavin.gavhack.mods.*;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public final class ModuleManager {

    private final HashMap<String, Module> nameMap = new HashMap<>();
    private final HashMap<Class<? extends Module>, Module> classMap = new HashMap<>();
    private final ArrayList<Module> moduleList = new ArrayList<>();

    public ModuleManager() {
        // register mods
        register(new KillAura());
        register(new Fullbright());
        register(new ClickGui());
        register(new NoCaveCulling());

        // sort modules alphabetically
        moduleList.sort(this::sortAlphabetically);
    }

    public <T extends Module> T getModule(String name) {
        return  (T) nameMap.get(name);
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) classMap.get(clazz);
    }

    public ArrayList<Module> getModuleList() {
        return moduleList;
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
