package me.gavin.gavhack.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.world.World;

public interface MC {

    Minecraft mc = Minecraft.getMinecraft();

    FontRenderer fr = mc.fontRenderer;

    EntityPlayerSP player = mc.player;

    World world = mc.world;

    PlayerControllerMP controller = mc.playerController;
}
