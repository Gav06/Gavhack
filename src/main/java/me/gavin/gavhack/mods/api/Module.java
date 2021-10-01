package me.gavin.gavhack.mods.api;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhack.misc.Bind;
import me.gavin.gavhack.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public abstract class Module extends Bind {


    protected final ArrayList<Setting> settings = new ArrayList<>();
    protected final Minecraft mc = Minecraft.getMinecraft();

    private boolean enabled;

    private final String name;
    private final String desc;
    private final Category category;

    public Module(String name, String desc, Category category) {
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.enabled = false;
    }

    public void enable() {
        enabled = true;
        EventManager.register(this);
        onEnable();
    }

    public void disable() {
        enabled = false;
        EventManager.unregister(this);
        onDisable();
    }

    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }

    public void setEnabled(boolean flag) {
        if (flag) {
            enable();
        } else {
            disable();
        }
    }

    protected void onEnable() { }

    protected void onDisable() { }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public enum Category {
        Combat,
        Movement,
        Render,
        World,
        Misc,
        Client
    }
}

