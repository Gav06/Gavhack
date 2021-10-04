package me.gavin.gavhack.mods.api;

import me.gavin.gavhack.Gavhack;
import me.gavin.gavhack.misc.Bind;
import me.gavin.gavhack.misc.MC;
import me.gavin.gavhack.settings.Setting;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public abstract class Module extends Bind implements MC {


    protected final ArrayList<Setting> settings = new ArrayList<>();
    protected final Gavhack gavhack = Gavhack.INSTANCE;

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
        MinecraftForge.EVENT_BUS.register(this);
        onEnable();
    }

    public void disable() {
        enabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
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

