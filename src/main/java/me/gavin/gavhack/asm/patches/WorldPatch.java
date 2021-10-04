package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.tree.*;

public class WorldPatch extends Patch {

    public WorldPatch() {
        super(Classes.World);

        this.methodPatches.add(spawnParticlePatch);
    }

    private final MethodPatch spawnParticlePatch = new MethodPatch(Methods.World$spawnParticle, node -> {
        final InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(ILOAD, 1));
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "World$spawnParticle", "(I)Z", false));
        final LabelNode jump = new LabelNode();
        insnList.add(new JumpInsnNode(IFEQ, jump));
        insnList.add(new InsnNode(RETURN));
        insnList.add(jump);

        node.instructions.insert(insnList);
    });
}
