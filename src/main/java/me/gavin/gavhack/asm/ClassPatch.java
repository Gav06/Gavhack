package me.gavin.gavhack.asm;

import org.objectweb.asm.tree.ClassNode;

@FunctionalInterface
public interface ClassPatch {

    void patch(ClassNode node);
}