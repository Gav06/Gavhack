package dev.gavhack.module;

import dev.gavhack.settings.Setting;
import dev.gavhack.util.ClassFinder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ModuleManager {

    private final List<Module> modules;
    private final HashMap<Class<? extends Module>, Module> moduleMap;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.moduleMap = new HashMap<>();

        Set<Class<?>> classes = ClassFinder.findClassesInPackage("dev.gavhack.module.mods");

        if (classes == null) {
            throw new RuntimeException("Failed to load modules! Is the package being searched correct?");
        }
        for (Class<?> clazz : classes) {
            if (Module.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                for (Constructor<?> constructor : clazz.getConstructors()) {
                    if (constructor.getParameterCount() == 0) {
                        try {
                            addModule((Module) constructor.newInstance());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        modules.sort(this::sortAlphabetically);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private int sortAlphabetically(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }

    private void addModule(Module module) {
        // find and register settings
        for (Field field : module.getClass().getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            if (Setting.class.isAssignableFrom(field.getType())) {
                try {
                    module.getSettings().add((Setting) field.get(module));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        this.modules.add(module);
        this.moduleMap.put(module.getClass(), module);
    }

    public List<Module> getModules() {
        return modules;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) moduleMap.get(clazz);
    }

    public boolean isModuleEnabled(Class<? extends Module> module) {
        return getModule(module).isEnabled();
    }

    public List<Module> getCategoryModules(Category category) {
        final List<Module> list = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                list.add(module);
            }
        }

        return list;
    }

    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() != Keyboard.KEY_NONE) {
            for (Module module : modules) {
                if (module.getBindKey() == Keyboard.getEventKey()) {
                    module.toggle();
                }
            }
        }
    }
}
