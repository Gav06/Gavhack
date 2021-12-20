package dev.gavhack.module;

import com.google.gson.JsonObject;
import dev.gavhack.Gavhack;
import dev.gavhack.config.Configurable;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public abstract class Module implements Configurable {

    protected final ArrayList<Setting> settings = new ArrayList<>();
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final Gavhack gavhack = Gavhack.INSTANCE;;

    private final String name;
    private final String description;
    private final Category category;

    private int bind;
    private boolean enabled;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.bind = Keyboard.KEY_NONE;
        this.enabled = false;
        this.gavhack.getConfigManager().addConfigurable(this);
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
        return bind;
    }

    public void setBindKey(int bind) {
        this.bind = bind;
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

    @Override
    public String getConfigName() {
        return this.name + "Mod";
    }

    @Override
    public void writeToConfig(JsonObject jsonObject) {
        jsonObject.addProperty("bind", bind);
        jsonObject.addProperty("enabled", enabled);

        for (Setting setting : settings) {
            if (setting instanceof BooleanSetting) {
                jsonObject.addProperty(setting.getName(), ((BooleanSetting)setting).isEnabled());
            } else if (setting instanceof EnumSetting) {
                jsonObject.addProperty(setting.getName(), ((EnumSetting<?>)setting).getValue().ordinal());
            } else if (setting instanceof FloatSetting) {
                jsonObject.addProperty(setting.getName(), ((FloatSetting)setting).getValue());
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void readFromConfig(JsonObject jsonObject) {
        this.setBindKey(jsonObject.get("bind").getAsInt());

        if (jsonObject.get("enabled").getAsBoolean())
            this.enable();

        for (Setting setting : settings) {
            if (!jsonObject.has(setting.getName()))
                continue;

            if (setting instanceof BooleanSetting) {
                ((BooleanSetting)setting).setValue(jsonObject.get(setting.getName()).getAsBoolean());
            } else if (setting instanceof EnumSetting) {
                final EnumSetting enumSetting = (EnumSetting) setting;
                int ordinal = jsonObject.get(setting.getName()).getAsInt();

                if (ordinal <= enumSetting.getValues().length - 1) {
                    enumSetting.setValue(enumSetting.getValues()[ordinal]);
                }
            } else if (setting instanceof FloatSetting) {
                jsonObject.addProperty(setting.getName(), ((FloatSetting)setting).getValue());
            }
        }
    }
}