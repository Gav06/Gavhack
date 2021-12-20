package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Sprint extends Module {

    private final EnumSetting<SprintMode> mode = new EnumSetting<>("Mode", SprintMode.Legit);

    public Sprint() {
        super("Sprint", "Sprint automatically", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        mc.player.setSprinting(shouldSprint());
    }

    public boolean shouldSprint() {
        return !mc.player.isSneaking()
                && !mc.player.isElytraFlying()
                && mc.player.getFoodStats().getFoodLevel() > 6
                && checkInput();
    }

    private boolean checkInput() {
        if (mode.getValue() == SprintMode.Rage)
            return mc.player.moveForward != 0 || mc.player.moveStrafing != 0;
        else
            return mc.player.movementInput.moveForward > 0.0f;
    }

    @Override
    public String getExtraInfo() {
        return "[" + mode.getValue().name() + "]";
    }

    public enum SprintMode {
        Legit,
        Rage
    }
}
