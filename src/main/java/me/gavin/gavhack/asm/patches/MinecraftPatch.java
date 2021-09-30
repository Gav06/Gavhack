package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.ASMHelper;
import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class MinecraftPatch extends Patch {
    public MinecraftPatch() {
        super(ASM.Classes.Minecraft);

        methodPatches.add(runTickKeyboardPatch);
    }

    private final MethodPatch runTickKeyboardPatch = new MethodPatch(ASM.Methods.Minecraft$runTickKeyboard$NAME, ASM.Methods.Minecraft$runTickKeyboard$DESC, node -> {

        final InsnList targetInsns = ASMHelper.findPatternInsn(node.instructions,
                F_NEW,
                F_NEW,
                ILOAD,
                IFEQ,
                F_NEW);

        final InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(ILOAD, 1));
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "Minecraft$runTickKeyboard", "(I)V", false));

        node.instructions.insert(targetInsns.getLast(), insnList);
    });
}
