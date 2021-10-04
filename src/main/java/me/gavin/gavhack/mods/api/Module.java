package me.gavin.gavhack.mods.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.gavin.gavhack.Gavhack;
import me.gavin.gavhack.config.Configurable;
import me.gavin.gavhack.misc.Bind;
import me.gavin.gavhack.misc.MC;
import me.gavin.gavhack.mods.UnfocusedCPU;
import me.gavin.gavhack.settings.BooleanSetting;
import me.gavin.gavhack.settings.FloatSetting;
import me.gavin.gavhack.settings.ModeSetting;
import me.gavin.gavhack.settings.Setting;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public abstract class Module extends Bind implements MC, Configurable {


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

    @Override
    public void saveProperties(JsonObject object) {

        object.addProperty("bind", getBind());
        object.addProperty("enabled", enabled);
        for (Setting setting : settings) {
            if (setting instanceof BooleanSetting) {
                object.addProperty(setting.getName(), ((BooleanSetting) setting).isEnabled());
            } else if (setting instanceof FloatSetting) {
                object.addProperty(setting.getName(), ((FloatSetting) setting).getValue());
            } else if (setting instanceof ModeSetting) {
                object.addProperty(setting.getName(), ((ModeSetting) setting).getIndex());
            }
        }
    }

    @Override
    public void loadProperties(JsonObject object) {
        this.setBind(object.get("bind").getAsInt());
        this.setEnabled(object.get("enabled").getAsBoolean());
        for (Setting setting : settings) {
            if (setting != null) {
                if (object.get(setting.getName()) != null) {
                    if (setting instanceof BooleanSetting) {
                        ((BooleanSetting) (setting)).setEnabled(object.get(setting.getName()).getAsBoolean());
                    } else if (setting instanceof FloatSetting) {
                        ((FloatSetting) (setting)).setValue(object.get(setting.getName()).getAsFloat());
                    } else if (setting instanceof ModeSetting) {
                        final ModeSetting modeSetting = (ModeSetting) setting;
                        int index = modeSetting.indexOf(object.get(setting.getName()).getAsString());
                        if (index != -1) {
                            modeSetting.setIndex(index);
                        }
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String getConfigGroup() {
        return "mods";
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

    protected boolean shouldRun() {
        return !gavhack.getModuleManager().getModule(UnfocusedCPU.class).isEnabled();
    }
}