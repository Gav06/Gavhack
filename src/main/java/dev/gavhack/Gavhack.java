package dev.gavhack;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = Gavhack.MOD_ID,
        name = Gavhack.MOD_NAME,
        version = Gavhack.VERSION
)
public class Gavhack {

    public static final String MOD_ID = "gavhack";
    public static final String MOD_NAME = "Gavhack";
    public static final String VERSION = "3.0";

    /** This is the instance of your mod as created by Forge. It will never be null. */
    @Mod.Instance(MOD_ID)
    public static Gavhack INSTANCE;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
