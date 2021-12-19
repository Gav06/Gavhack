package dev.gavhack.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import dev.gavhack.Gavhack;
import dev.gavhack.module.mods.GameActivity;
import net.minecraft.client.Minecraft;

public class DiscordUtil {

    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    private static final String APPID = "922196603790565397";

    private static Thread updateThread = null;

    public static void startRPC() {
        rpc.Discord_ClearPresence();
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRichPresence richPresence = new DiscordRichPresence();

        richPresence.startTimestamp = Gavhack.clientStartTime / 1000L;
        richPresence.largeImageKey = "toupe";

        rpc.Discord_Initialize(APPID, handlers, true, "");

        updateThread = new Thread(() -> {
           while (!Thread.currentThread().isInterrupted()) {
                rpc.Discord_RunCallbacks();
                rpc.Discord_UpdatePresence(richPresence);
                final Minecraft mc = Minecraft.getMinecraft();

                if (GameActivity.ip.isEnabled()) {
                    if (mc.player != null) {
                        if (mc.getCurrentServerData() != null) {
                            richPresence.details = "Playing on " + mc.getCurrentServerData().serverIP;
                        } else {
                            richPresence.details = "Playing Singeplayer";
                        }
                    } else {
                        richPresence.details = "In the menus";
                    }
                } else {
                    richPresence.details = "Playing Minecraft";
                }

                if (GameActivity.username.isEnabled()) {
                    richPresence.state = Gavhack.MOD_NAME + " " + Gavhack.VERSION + " - " + mc.getSession().getUsername();
                } else {
                    richPresence.state = Gavhack.MOD_NAME + " " + Gavhack.VERSION;
                }

               try { Thread.sleep(2000L); } catch (InterruptedException ignored) { }
           }
        });

        updateThread.start();
    }

    public static void stopRPC() {
        if (updateThread == null) {
            return;
        }

        updateThread.interrupt();
        updateThread = null;
        rpc.Discord_ClearPresence();
        rpc.Discord_Shutdown();
    }
}