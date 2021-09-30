package me.gavin.gavhack.asm;

public class ASMName implements IName {

    private final String mcpName;
    private final String notchName;

    public ASMName(String mcpName, String notchName) {
        this.mcpName = mcpName;
        this.notchName = notchName;
    }

    public ASMName(String name) {
        this.mcpName = this.notchName = name;
    }

    public String getMcpName() {
        return mcpName;
    }

    public String getNotchName() {
        return notchName;
    }

    @Override
    public String getCurrentName(boolean isObfuscated) {
        return isObfuscated ? notchName : mcpName;
    }
}
