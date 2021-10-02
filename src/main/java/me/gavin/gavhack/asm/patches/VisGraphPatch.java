package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.tree.*;

public final class VisGraphPatch extends Patch {
    public VisGraphPatch() {
        super(Classes.VisGraph);

        methodPatches.add(setOpaqueCubePatch);
    }

    private final MethodPatch setOpaqueCubePatch = new MethodPatch(Methods.VisGraph$setOpaqueCube, node -> {
        final InsnList insnList = new InsnList();
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "VisGraph$setOpaqueCube", "()Z", false));
        final LabelNode cancelLabel = new LabelNode();
        insnList.add(new JumpInsnNode(IFEQ, cancelLabel));
        insnList.add(new InsnNode(RETURN));
        insnList.add(cancelLabel);

        node.instructions.insert(insnList);
    });
}
