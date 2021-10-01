package me.gavin.gavhack.asm.naming;

// where all mapped and obfuscated names for reflections and asm are stored

@SuppressWarnings("all")
public interface Names {
    interface ASM {
        interface Classes {
            ASMClassName Minecraft = new ASMClassName("net.minecraft.client.Minecraft", "bib");
            ASMClassName EntityPlayerSP = new ASMClassName("net.minecraft.client.entity.EntityPlayerSP", "bud");
            ASMClassName ScaledResolution = new ASMClassName("net.minecraft.client.gui.ScaledResolution", "bit");
            ASMClassName GuiIngameForge = new ASMClassName("net.minecraftforge.client.GuiIngameForge");
            ASMClassName NetworkManager = new ASMClassName("net.minecraft.network.NetworkManager", "gw");
            ASMClassName Packet = new ASMClassName("net.minecraft.network.Packet", "ht");
            ASMClassName EntityRenderer = new ASMClassName("net.minecraft.client.renderer.EntityRenderer", "buo");
        }
        interface Methods {
            ASMName Minecraft$init$NAME = new ASMName("<init>");
            ASMName Minecraft$init$DESC = new ASMName("(Lnet/minecraft/client/main/GameConfiguration;)V", "(Lboz;)V");
            ASMName Minecraft$runTickKeyboard$NAME = new ASMName("runTickKeyboard", "aD");
            ASMName Minecraft$runTickKeyboard$DESC = new ASMName("()V");

            ASMName EntityPlayersP$onLivingUpdate$NAME = new ASMName("onLivingUpdate", "n");
            ASMName EntityPlayerSP$onLivingUpdate$DESC = new ASMName("()V");

            ASMName GuiIngameForge$renderGameOverlay$NAME = new ASMName("renderGameOverlay", "a");
            ASMName GuiIngameForge$renderGameOverlay$DESC = new ASMName("(F)V");

            ASMName NetworkManager$sendPacket$NAME = new ASMName("sendPacket", "a");
            ASMName NetworkManager$sendPacket$DESC = new ASMName("(Lnet/minecraft/network/Packet;)V", "");
            ASMName NetworkManager$channelRead0$NAME = new ASMName("channelRead0", "a");
            ASMName NetworkManager$channelRead0$DESC = new ASMName("(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", "(Lio/netty/channel/ChannelHandlerContext;Lht;)V");

            ASMName EntityRenderer$updateLightmap$NAME = new ASMClassName("updateLightmap", "g");
            ASMName EntityRenderer$updateLightmap$DESC = new ASMClassName("(F)V");
        }
    }
//
//    interface Reflection {
//        interface Fields {
//            ReflectionName Minecraft$rightClickDelayTimer = new ReflectionName("rightClickDelayTimer", "field_71467_ac");
//        }
//        interface Methods {
//
//        }
//    }
}