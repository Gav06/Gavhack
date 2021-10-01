package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.ASMHelper;
import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class EntityRendererPatch extends Patch {
    public EntityRendererPatch() {
        super(Classes.EntityRenderer);

        methodPatches.add(updateLightmapPatch);
    }

    private final MethodPatch updateLightmapPatch = new MethodPatch(Methods.EntityRenderer$updateLightmap, node -> {
        final VarInsnNode target = ASMHelper.findVarInsn(node, FSTORE, 16, 1);

        final InsnList insnList = new InsnList();
        // load the variable back
        insnList.add(new VarInsnNode(FLOAD, 16));
        // call hook function
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "EntityRenderer$updateLightmap", "(F)F", false));
        // store result of hook function into variable
        insnList.add(new VarInsnNode(FSTORE, 16));

        // insert bytecode after target
        node.instructions.insert(target, insnList);
    });
}
