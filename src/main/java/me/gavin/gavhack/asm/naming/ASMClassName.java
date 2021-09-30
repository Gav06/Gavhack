package me.gavin.gavhack.asm.naming;

public class ASMClassName extends ASMName {

    public ASMClassName(String mcpName, String notchName) {
        super(mcpName, notchName);
    }

    public ASMClassName(String name) {
        super(name);
    }

    public String getInternalName(boolean obf, boolean desc) {
        String s = getCurrentName(obf).replaceAll("\\.", "/");
        if (desc) {
            return "L" + s + ";";
        } else {
            return s;
        }
    }
}
