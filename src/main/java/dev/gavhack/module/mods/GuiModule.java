package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.settings.EnumSetting;
import org.lwjgl.input.Keyboard;

public class GuiModule extends Module {

    public static final BooleanSetting sound = new BooleanSetting("Sound", true);
    public static final BooleanSetting descriptions = new BooleanSetting("Descriptions", true);
    public static final BooleanSetting background = new BooleanSetting("Background", true);
    public static final EnumSetting<OpenAnimation> openAnimation = new EnumSetting<>("Animation", OpenAnimation.None);

    public GuiModule() {
        super("Gui", "Opens the client GUI", Category.CLIENT);
        setBindKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(gavhack.getGui());
        disable();
    }

    public enum OpenAnimation {
        None,
        MoneyMod
    }
}
