package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.ASMHelper;
import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

public final class TimerPatch extends Patch {
    public TimerPatch() {
        super(Classes.Timer);

        methodPatches.add(updateTimerPatch);
    }

    private final MethodPatch updateTimerPatch = new MethodPatch(Methods.Timer$updateTimer, node -> {
        final InsnList insns = ASMHelper.findPatternInsn(node.instructions,
                ALOAD,
                LLOAD,
                ALOAD,
                GETFIELD,
                LSUB,
                L2F,
                ALOAD,
                GETFIELD);

        final InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "Timer$updateTimer", "()F", false));
        insnList.add(new InsnNode(FMUL));

        node.instructions.insert(insns.getLast(), insnList);
    });
}
