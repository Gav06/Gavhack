package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

public final class EntityPlayerSPPatch extends Patch {
    public EntityPlayerSPPatch() {
        super(Classes.EntityPlayerSP);

        methodPatches.add(onLivingUpdatePatch);
    }

    private final MethodPatch onLivingUpdatePatch = new MethodPatch(Methods.EntityPlayersP$onLivingUpdate$NAME, Methods.EntityPlayerSP$onLivingUpdate$DESC, node -> {
        final InsnList insnList0 = new InsnList();
        insnList0.add(new InsnNode(ICONST_0));
        insnList0.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(Hooks.class), "EntityPlayerSP$onLivingUpdate", "(I)V", false));
        node.instructions.insert(insnList0);
    });
}
