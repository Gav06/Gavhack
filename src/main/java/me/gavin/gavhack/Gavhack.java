package me.gavin.gavhack;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        name = Gavhack.NAME,
        modid = Gavhack.ID,
        version = Gavhack.VERSION,
        clientSideOnly = true,
        acceptedMinecraftVersions = "[1.12.2]"
)
public final class Gavhack {

    public static final String NAME = "Gavhack";
    public static final String ID = "gavhack";
    public static final String VERSION = "2.0";

    public Logger logger;

    @Mod.Instance(ID)
    public static Gavhack INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = LogManager.getLogger(ID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EventManager.register(this);
    }

    @EventTarget
    public void onTick(PlayerLivingUpdateEvent event) {
        System.out.println("wowie");
    }

    public Logger getLogger() {
        return logger;
    }
}
