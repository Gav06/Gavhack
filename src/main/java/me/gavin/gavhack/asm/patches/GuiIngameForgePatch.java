package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.*;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class GuiIngameForgePatch extends Patch {
    public GuiIngameForgePatch() {
        super(Classes.GuiIngameForge);

        methodPatches.add(renderGameOverlayPatch);
    }

    private final MethodPatch renderGameOverlayPatch = new MethodPatch(Methods.GuiIngameForge$renderGameOverlay$NAME, Methods.GuiIngameForge$renderGameOverlay$DESC, node -> {
        final InsnList insnList = new InsnList();
        // partialTicks
        insnList.add(new VarInsnNode(FLOAD, 1));
        // scaledResolution
        insnList.add(new VarInsnNode(ALOAD, 0));
        insnList.add(new FieldInsnNode(GETFIELD, Classes.GuiIngameForge.getInternalName(false, false), "res", Classes.ScaledResolution.getInternalName(isObf(), true)));
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "GuiIngameForge$renderGameOverlay", "(F" + Classes.ScaledResolution.getInternalName(isObf(), true) + ")V", false));

        node.instructions.insertBefore(ASMHelper.findBottom(node), insnList);
    });
}