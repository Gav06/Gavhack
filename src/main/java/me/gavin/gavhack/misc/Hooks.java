package me.gavin.gavhack.misc;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhack.events.PlayerLivingUpdateEvent;

public class Hooks {

    public static void EntityPlayerSP$onLivingUpdate(int stage) {
        if (stage == 0) {
            EventManager.call(new PlayerLivingUpdateEvent());
        }
    }
}
