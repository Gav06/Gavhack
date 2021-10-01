package me.gavin.gavhack.asm;

import me.gavin.gavhack.asm.naming.ASMClassName;
import me.gavin.gavhack.asm.naming.ASMName;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ASMHelper {

    public static final HashMap<Integer, String> OPCODES = new HashMap<>();

    static {
        try {
            for (Field field : Opcodes.class.getDeclaredFields()) {
                OPCODES.put((int) field.get(null), field.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] nodeToBytes(ClassNode node) {
        final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }

    public static ClassNode bytesToNode(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.EXPAND_FRAMES);
        return node;
    }

    public static AbstractInsnNode findBottom(MethodNode method) {
        return method.instructions.get(method.instructions.size() - 2);
    }

    public static MethodInsnNode findMethodInsn(MethodNode parentMethod, ASMClassName owner, ASMName name, ASMName desc, int ordinal) {
        final List<MethodInsnNode> list = new ArrayList<>();
        final boolean obf = ASMTransformer.patchManager.isObfuscated;
        for (AbstractInsnNode insn : parentMethod.instructions.toArray()) {
            if (insn instanceof MethodInsnNode) {
                final MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.owner.equals(owner.getActualName(obf)) && methodInsn.name.equals(name.getActualName(obf)) && methodInsn.desc.equals(desc.getActualName(obf))) {
                    list.add(methodInsn);
                }
            }
        }

        return list.get(ordinal);
    }

    public static VarInsnNode findVarInsn(MethodNode parentMethod, int opcode, int variable, int ordinal) {
        final List<VarInsnNode> list = new ArrayList<>();
        for (AbstractInsnNode insn : parentMethod.instructions.toArray()) {
            if (insn instanceof VarInsnNode) {
                final VarInsnNode varInsn = (VarInsnNode) insn;
                if (varInsn.getOpcode() == opcode && varInsn.var == variable) {
                    list.add(varInsn);
                }
            }
        }

        return list.get(ordinal);
    }

    public static InsnList findPatternInsn(InsnList insns, int... bytecodes) {
        int patternIndex = 0;
        final InsnList list = new InsnList();

        for (AbstractInsnNode node : insns.toArray()) {
            if (patternIndex > bytecodes.length - 1) {
                return list;
            }

            if (node.getOpcode() == bytecodes[patternIndex]) {
                patternIndex++;
                list.add(node);
            } else {
                if (patternIndex > 0) {
                    list.clear();
                    patternIndex = 0;
                }
            }
        }

        return list;
    }
}