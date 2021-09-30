package me.gavin.gavhack.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

public class ASMLoader implements IFMLLoadingPlugin {

    public boolean isObfuscatedEnvironment;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
                ASMTransformer.class.getName()
        };
    }

    @Nullable
    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isObfuscatedEnvironment = (boolean) data.get("runtimeDeobfuscationEnabled");
        ASMTransformer.patchManager = new PatchManager(isObfuscatedEnvironment);
        PatchManager.asmLogger.info("Is deobfuscated environment: " + isObfuscatedEnvironment);
    }

    @Nullable
    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
