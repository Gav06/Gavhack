package me.gavin.gavhack.asm;

// where all mapped and obfuscated names for reflections and asm are stored
public interface Names {
    interface ASM {
        interface Classes {
            ASMName Minecraft = new ASMName("net.minecraft.client.Minecraft", "bhz");
            ASMName EntityPlayerSP = new ASMName("net.minecraft.client.entity.EntityPlayerSP", "bud");
        }
        interface Methods {
            ASMName Minecraft$init$NAME = new ASMName("<init>");
            ASMName Minecraft$init$DESC = new ASMName("(Lnet/minecraft/client/main/GameConfiguration;)V", "(Lboz;)V");

            ASMName EntityPlayersP$onLivingUpdate$NAME = new ASMName("onLivingUpdate", "n");
            ASMName EntityPlayerSP$onLivingUpdate$DESC = new ASMName("()V");
        }
    }

    interface Reflection {
        interface Fields {

        }
        interface Methods {

        }
    }
}
