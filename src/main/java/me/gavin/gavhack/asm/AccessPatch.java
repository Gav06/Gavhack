package me.gavin.gavhack.asm;

import me.gavin.gavhack.asm.naming.ASMName;

public class AccessPatch {

    private final ASMName name;
    private final ASMName desc;
    private final int access;
    private final MemberType memberType;

    public AccessPatch(ASMName name, ASMName desc, int access, MemberType memberType) {
        this.name = name;
        this.desc = desc;
        this.access = access;
        this.memberType = memberType;
    }

    public int getAccess() {
        return access;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public ASMName getName() {
        return name;
    }

    public ASMName getDesc() {
        return desc;
    }

    public enum MemberType {
        FIELD,
        METHOD
    }
}
