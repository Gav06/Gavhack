package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.Stitcher;

public class StitcherException extends RuntimeException
{
    private final Stitcher.Holder field_98149_a;

    public StitcherException(Stitcher.Holder par1StitchHolder, String par2Str)
    {
        super(par2Str);
        this.field_98149_a = par1StitchHolder;
    }
}
