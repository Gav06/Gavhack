package me.gavin.gavhack.asm;

import me.gavin.gavhack.asm.naming.ASMName;
import org.objectweb.asm.tree.MethodNode;

public class MethodPatch {

    private final ASMName name;
    private final ASMName descriptor;
    private final MethodPatchBehavior behavior;

    public MethodPatch(ASMName name, ASMName desc, MethodPatchBehavior behavior) {
        this.name = name;
        this.descriptor = desc;
        this.behavior = behavior;
    }

    public void run(MethodNode node) {
        behavior.patch(node);
    }

    public ASMName getName() {
        return name;
    }

    public MethodPatchBehavior getBehavior() {
        return behavior;
    }

    public ASMName getDescriptor() {
        return descriptor;
    }

//    @Retention(RetentionPolicy.RUNTIME)
//    @Target(ElementType.FIELD)
//    public @interface Add {
//    }
}
