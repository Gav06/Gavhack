package me.gavin.gavhack.asm;

import org.objectweb.asm.tree.MethodNode;

@FunctionalInterface
public interface MethodPatchBehavior {

    void patch(MethodNode methodNode);
}
