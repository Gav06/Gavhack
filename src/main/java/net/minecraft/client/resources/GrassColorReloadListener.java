package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;

public class GrassColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");

    public void onResourceManagerReload(IResourceManager par1ResourceManager)
    {
        try
        {
            ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(par1ResourceManager, field_130078_a));
        }
        catch (IOException var3)
        {
            ;
        }
    }
}
