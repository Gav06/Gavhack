package me.gavin.gavhack.asm.patches;

import me.gavin.gavhack.asm.MethodPatch;
import me.gavin.gavhack.asm.Patch;
import me.gavin.gavhack.misc.Hooks;
import org.objectweb.asm.tree.*;

public final class NetworkManagerPatch extends Patch {
    public NetworkManagerPatch() {
        super(Classes.NetworkManager);

        methodPatches.add(sendPacketPatch);
        methodPatches.add(channelRead0Patch);
    }


    private final MethodPatch sendPacketPatch = new MethodPatch(Methods.NetworkManager$sendPacket, node -> {
        final InsnList insnList = new InsnList();

        insnList.add(new VarInsnNode(ALOAD, 1));
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "NetworkManager$sendPacket", "(" + Classes.Packet.getInternalName(isObf(), true) + ")Z", false));
        final LabelNode label = new LabelNode();
        insnList.add(new JumpInsnNode(IFEQ, label));
        insnList.add(new InsnNode(RETURN));
        insnList.add(label);

        node.instructions.insert(insnList);
    });

    private final MethodPatch channelRead0Patch = new MethodPatch(Methods.NetworkManager$channelRead0, node -> {
        final InsnList insnList = new InsnList();

        insnList.add(new VarInsnNode(ALOAD, 2));
        insnList.add(new MethodInsnNode(INVOKESTATIC, Hooks.INTERNAL_NAME, "NetworkManager$channelRead0", "(" + Classes.Packet.getInternalName(isObf(), true) + ")Z", false));
        final LabelNode label = new LabelNode();
        insnList.add(new JumpInsnNode(IFEQ, label));
        insnList.add(new InsnNode(RETURN));
        insnList.add(label);

        node.instructions.insert(insnList);
    });
}
