package dev.gavhack.module;

import dev.gavhack.Gavhack;
import dev.gavhack.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public abstract class Module {

    protected final ArrayList<Setting> settings = new ArrayList<>();
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final Gavhack gavhack = Gavhack.INSTANCE;;

    private final String name;
    private final String description;
    private final Category category;

    private KeyBinding bind;
    private boolean enabled;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.bind = new KeyBinding(name, Keyboard.KEY_NONE, Gavhack.MOD_NAME);
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }

    public void enable() {
        if (!enabled) {
            enabled = true;
            MinecraftForge.EVENT_BUS.register(this);
            onEnable();
        }
    }

    public void disable() {
        if (enabled) {
            enabled = false;
            MinecraftForge.EVENT_BUS.unregister(this);
            onDisable();
        }
    }

    protected void onEnable() { }

    protected void onDisable() { }

    public int getBindKey() {
        return bind.getKeyCode();
    }

    public KeyBinding getKeyBinding() {
        return bind;
    }

    public void setBindKey(int bind) {
        this.bind.setKeyCode(bind);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public String getExtraInfo() {
        return "";
    }
}