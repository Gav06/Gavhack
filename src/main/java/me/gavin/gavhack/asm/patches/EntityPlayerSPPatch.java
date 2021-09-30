package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

public class EntityPlayerSPPatch extends Patch {
    public EntityPlayerSPPatch() {
        super(ASM.Classes.EntityPlayerSP);

        methodPatches.add(onLivingUpdatePatch);
    }

    private final MethodPatch onLivingUpdatePatch = new MethodPatch(ASM.Methods.EntityPlayersP$onLivingUpdate$NAME, ASM.Methods.EntityPlayerSP$onLivingUpdate$DESC, node -> {
        final InsnList insnList = new InsnList();
        insnList.add(new InsnNode(ICONST_0));
        insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(Hooks.class), "EntityPlayerSP$onLivingUpdate", "(I)V", false));
        node.instructions.insert(insnList);
    });
}
