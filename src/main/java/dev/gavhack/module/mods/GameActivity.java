package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.util.DiscordUtil;

public class GameActivity extends Module {

    public static final BooleanSetting username = new BooleanSetting("Username", true);
    public static final BooleanSetting ip = new BooleanSetting("IP", true);

    public GameActivity() {
        super("GameActivity", "Game activity for discord", Category.CLIENT);
    }

    @Override
    protected void onEnable() {
        DiscordUtil.startRPC();
    }

    @Override
    protected void onDisable() {
        DiscordUtil.stopRPC();
    }
}
