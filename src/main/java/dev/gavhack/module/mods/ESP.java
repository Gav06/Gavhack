package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP extends Module {

    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting animals = new BooleanSetting("Animals", true);
    private final BooleanSetting mobs = new BooleanSetting("Mobs", true);

    public ESP() {
        super("ESP", "esp", Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {

    }


}