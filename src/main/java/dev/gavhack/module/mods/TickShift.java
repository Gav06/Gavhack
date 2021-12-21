//package dev.gavhack.module.mods;
//
//import dev.gavhack.events.PacketEvent;
//import dev.gavhack.module.Category;
//import dev.gavhack.module.Module;
//import dev.gavhack.settings.BooleanSetting;
//import dev.gavhack.settings.FloatSetting;
//import net.minecraft.network.play.client.CPacketPlayer;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//
//public class TickShift extends Module {
//
//    private final BooleanSetting cancelPackets = new BooleanSetting("CancelPackets", false);
//    private final FloatSetting ticks = new FloatSetting("StartTicks", 26f, 10f, 150f);
//
//    public TickShift() {
//        super("TickShift", "fancy timer bypass", Category.MOVEMENT);
//    }
//
//    private int lagTicks = 0;
//    private boolean resetTicks = false;
//    private boolean shouldUseTimer = false;
//
//    @SubscribeEvent
//    public void onPacket(PacketEvent.Send event) {
//        if (event.getPacket() instanceof CPacketPlayer
//                || event.getPacket() instanceof CPacketPlayer.Position
//                || event.getPacket() instanceof CPacketPlayer.Rotation
//                || event.getPacket() instanceof CPacketPlayer.PositionRotation) {
//            if (cancelPackets.isEnabled()) {
//                event.setCanceled(true);
//                if (lagTicks > ticks.getValue())
//                    resetTicks = true;
//            } else {
//                resetTicks = true;
//            }
//        }
//    }
//
//    private float prevTimerSpeed = Timer.speed.getValue();
//
//    @SubscribeEvent
//    public void onTick(TickEvent.ClientTickEvent event) {
//        if (resetTicks) {
//            lagTicks = 0;
//            resetTicks = false;
//            final Timer timer = gavhack.getModuleManager().getModule(Timer.class);
//            prevTimerSpeed = Timer.speed.getValue();
//
//        } else {
//            lagTicks++;
//        }
//    }
//
//    @Override
//    protected void onDisable() {
//        Timer.speed.setValue(prevTimerSpeed);
//    }
//
//    @Override
//    public String getExtraInfo() {
//        return "(" + lagTicks + ")";
//    }
//}
