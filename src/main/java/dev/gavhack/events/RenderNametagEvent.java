package dev.gavhack.events;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderNametagEvent extends Event {

    private final AbstractClientPlayer player;
    private final double x;
    private final double y;
    private final double z;
    private final String text;
    private final double distance;

    public RenderNametagEvent(AbstractClientPlayer player, double x, double y, double z, String text, double distance) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
        this.text = text;
        this.distance = distance;
    }

    public AbstractClientPlayer getPlayer() {
        return player;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getText() {
        return text;
    }

    public double getDistance() {
        return distance;
    }
}
