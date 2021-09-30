package me.gavin.gavhack.asm.naming;

import me.gavin.gavhack.asm.naming.IName;

public class ReflectionName implements IName {

    private final String mcpName;
    private final String srgName;

    public ReflectionName(String mcpName, String srgName) {
        this.mcpName = mcpName;
        this.srgName = srgName;
    }

    public String getSrgName() {
        return srgName;
    }

    public String getMcpName() {
        return mcpName;
    }

    // its ok to use srg name here, because we will be reflection after
    // forge has transformed all of the minecraft classes from notch to searge
    @Override
    public String getCurrentName(boolean isObfuscated) {
        return isObfuscated ? srgName : mcpName;
    }
}
