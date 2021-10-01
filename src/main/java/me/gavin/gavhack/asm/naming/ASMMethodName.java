package me.gavin.gavhack.asm.naming;

public class ASMMethodName {

    private final ASMName name;
    private final ASMName desc;

    public ASMMethodName(ASMName name, ASMName desc) {
        this.name = name;
        this.desc = desc;
    }

    public ASMName getName() {
        return name;
    }

    public ASMName getDesc() {
        return desc;
    }
}
