package me.gavin.gavhack.asm.naming;

// where all mapped and obfuscated names for reflections and asm are stored

import me.gavin.gavhack.asm.naming.ASMClassName;
import me.gavin.gavhack.asm.naming.ASMName;

@SuppressWarnings("all")
public interface Names {
    interface ASM {
        interface Classes {
            ASMClassName Minecraft = new ASMClassName("net.minecraft.client.Minecraft", "bhz");
            ASMClassName EntityPlayerSP = new ASMClassName("net.minecraft.client.entity.EntityPlayerSP", "bud");
            ASMClassName ScaledResolution = new ASMClassName("net.minecraft.client.gui.ScaledResolution", "bit");
            ASMClassName GuiIngameForge = new ASMClassName("net.minecraftforge.client.GuiIngameForge");
        }
        interface Methods {
            ASMName Minecraft$init$NAME = new ASMName("<init>");
            ASMName Minecraft$init$DESC = new ASMName("(Lnet/minecraft/client/main/GameConfiguration;)V", "(Lboz;)V");

            ASMName EntityPlayersP$onLivingUpdate$NAME = new ASMName("onLivingUpdate", "n");
            ASMName EntityPlayerSP$onLivingUpdate$DESC = new ASMName("()V");

            ASMName GuiIngameForge$renderGameOverlay$NAME = new ASMName("renderGameOverlay");
            ASMName GuiIngameForge$renderGameOverlay$DESC = new ASMName("(F)V");
        }
    }

    interface Reflection {
        interface Fields {

        }
        interface Methods {

        }
    }
}
