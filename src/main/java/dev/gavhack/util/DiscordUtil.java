package dev.gavhack.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordUtil {

    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    private static final String APPID = "922196603790565397";

    private static Thread updateThread = null;

    public static void startRPC() {
        rpc.Discord_ClearPresence();
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRichPresence richPresence = new DiscordRichPresence();

        rpc.Discord_Initialize(APPID, handlers, true, "");

        updateThread = new Thread(() -> {
           while (!Thread.currentThread().isInterrupted()) {
                rpc.Discord_RunCallbacks();

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