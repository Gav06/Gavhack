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
            ASMClassName VisGraph = new ASMClassName("net.minecraft.client.renderer.chunk.VisGraph", "bxs");
            ASMClassName Timer = new ASMClassName("net.minecraft.util.Timer", "bif");
            ASMClassName RenderLivingBase = new ASMClassName("net.minecraft.client.renderer.entity.RenderLivingBase", "bzy");
            ASMClassName Entity = new ASMClassName("net.minecraft.entity.Entity", "ve");
            ASMClassName EntityLivingBase = new ASMClassName("net.minecraft.entity.EntityLivingBase", "vn");
        }
        interface Methods {
            ASMMethodName Minecraft$init = new ASMMethodName(new ASMName("<init>"), new ASMName("(Lnet/minecraft/client/main/GameConfiguration;)V", "(Lboz;)V"));
            ASMMethodName Minecraft$runTickKeyboard = new ASMMethodName(new ASMName("runTickKeyboard", "aD"), new ASMName("()V"));

            ASMMethodName EntityPlayerSP$onLivingUpdate = new ASMMethodName(new ASMName("onLivingUpdate", "n"), new ASMName("()V"));

            ASMMethodName GuiIngameForge$renderGameOverlay = new ASMMethodName(new ASMName("renderGameOverlay", "a"), new ASMName("(F)V"));

            ASMMethodName NetworkManager$sendPacket = new ASMMethodName(new ASMName("sendPacket", "a"), new ASMName("(Lnet/minecraft/network/Packet;)V", "(Lht;)V"));
            ASMMethodName NetworkManager$channelRead0 = new ASMMethodName(new ASMName("channelRead0", "a"), new ASMName("(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", "(Lio/netty/channel/ChannelHandlerContext;Lht;)V"));

            ASMMethodName EntityRenderer$updateLightmap = new ASMMethodName(new ASMName("updateLightmap", "g"), new ASMName("(F)V"));

            ASMMethodName VisGraph$setOpaqueCube = new ASMMethodName(new ASMName("setOpaqueCube", " a"), new ASMName("(Lnet/minecraft/util/math/BlockPos;)V", "(Let;)V"));

            ASMMethodName Timer$updateTimer = new ASMMethodName(new ASMName("updateTimer", "a"), new ASMName("()V"));

            ASMMethodName RenderLivingBase$doRender = new ASMMethodName(new ASMName("doRender", "a"), new ASMName("(Lnet/minecraft/entity/Entity;DDDFF)V", "(Lve;DDDFF)V"));
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