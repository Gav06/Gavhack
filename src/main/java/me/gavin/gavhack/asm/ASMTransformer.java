package me.gavin.gavhack.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class ASMTransformer implements IClassTransformer {
    public static PatchManager patchManager;

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (patchManager != null) {
            Patch patch = patchManager.getPatch(name);
            if (patch != null) {
                PatchManager.asmLogger.info("Running on class " + name);
                ClassNode node = ASMHelper.bytesToNode(basicClass);
                final boolean obf = patchManager.isObfuscated;
                for (MethodNode methodNode : node.methods) {
                    for (MethodPatch methodPatch : patch.methodPatches) {
                        if (methodPatch.getName().getCurrentName(obf).equals(methodNode.name) && methodPatch.getDescriptor().getCurrentName(obf).equals(methodNode.desc)) {
                            methodPatch.run(methodNode);
                            PatchManager.asmLogger.info("Patching " + transformedName + "." + methodNode.name + methodNode.desc);
                        }
                    }

                    for (AccessPatch accessPatch : patch.accessPatches) {
                        if (accessPatch.getMemberType() == AccessPatch.MemberType.METHOD) {
                            if (accessPatch.getName().getCurrentName(obf).equals(methodNode.name) && accessPatch.getDesc().getCurrentName(obf).equals(methodNode.desc)) {
                                methodNode.access = accessPatch.getAccess();
                            }
                        }
                    }
                }

                for (FieldNode fieldNode : node.fields) {
                    for (AccessPatch accessPatch : patch.accessPatches) {
                        if (accessPatch.getMemberType() == AccessPatch.MemberType.FIELD) {
                            if (accessPatch.getName().getCurrentName(obf).equals(fieldNode.name) && accessPatch.getDesc().getCurrentName(obf).equals(fieldNode.desc)) {
                                fieldNode.access = accessPatch.getAccess();
                            }
                        }
                    }
                }

                for (ClassPatch classPatch : patch.classPatches) {
                    classPatch.patch(node);
                }
                return ASMHelper.nodeToBytes(node);
            }
        }
        return basicClass;
    }
}