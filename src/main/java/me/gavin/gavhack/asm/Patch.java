package me.gavin.gavhack.asm;

import me.gavin.gavhack.asm.naming.ASMName;
import me.gavin.gavhack.asm.naming.Names;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;

public abstract class Patch implements Opcodes, Names {


    private final ASMName name;
    protected final PatchManager patchManager = ASMTransformer.patchManager;
    protected final ArrayList<MethodPatch> methodPatches;
    protected final ArrayList<ClassPatch> classPatches;

    public Patch(ASMName className) {
        this.name = className;
        this.methodPatches = new ArrayList<>();
        this.classPatches = new ArrayList<>();
    }

    public ASMName getClassName() {
        return name;
    }

    protected boolean isObf() {
        return ASMTransformer.patchManager.isObfuscated;
    }
}
