package me.gavin.gavhack.asm;

import org.objectweb.asm.Opcodes;

import java.lang.reflect.Field;
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
        // scanning for method patches
//        for (Field field : getClass().getDeclaredFields()) {
//            if (!field.isAccessible())
//                field.setAccessible(true);
//
//            if (field.getType() == MethodPatch.class && field.isAnnotationPresent(MethodPatch.Add.class)) {
//                try {
//                    MethodPatch methodPatch = (MethodPatch) field.get(this);
//                    if (methodPatch != null) {
//                        methodPatches.add((MethodPatch) field.get(this));
//                    } else {
//                        patchManager.getAsmLogger().error("Failed to load a MethodPatch for " + name.getMcpName() + " patch");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    public ASMName getClassName() {
        return name;
    }

    protected boolean isObf() {
        return ASMTransformer.patchManager.isObfuscated;
    }
}
