package me.gavin.gavhack.asm;

import me.gavin.gavhack.asm.naming.ASMMethodName;
import me.gavin.gavhack.asm.naming.ASMName;
import org.objectweb.asm.tree.MethodNode;

public class MethodPatch {

    private final ASMMethodName name;
    private final MethodPatchBehavior behavior;

    public MethodPatch(ASMMethodName methodName, MethodPatchBehavior behavior) {
        this.name = methodName;
        this.behavior = behavior;
    }

    public void run(MethodNode node) {
        behavior.patch(node);
    }
    public MethodPatchBehavior getBehavior() {
        return behavior;
    }

    public ASMMethodName getName() {
        return name;
    }

//    @Retention(RetentionPolicy.RUNTIME)
//    @Target(ElementType.FIELD)
//    public @interface Add {
//    }
}
