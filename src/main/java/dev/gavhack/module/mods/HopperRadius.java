package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.MathUtil;
import dev.gavhack.util.RenderUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public class HopperRadius extends Module {

    private final FloatSetting renderRange = new FloatSetting("RenderRange", 15f, 50f, 7.5f);

    public HopperRadius() {
        super("HopperRadius", "Shows how far you can walk from hoppers without them closing", Category.RENDER);
        setBind(Keyboard.KEY_H);
    }

    private final Vec2f[] vectors = MathUtil.getCirclePoints(7.5f, 90);

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityHopper) {
                final TileEntityHopper hopper = (TileEntityHopper) tileEntity;
                if (tileEntity.getPos().getDistance((int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ) <= renderRange.getValue()) {
                    RenderUtil.prepareGL();
                    GlStateManager.glLineWidth(1.5f);
                    GlStateManager.color(1f, 0f, 0f, 1f);
                    final Tessellator tessellator = Tessellator.getInstance();
                    final BufferBuilder bufferBuilder = tessellator.getBuffer();
                    bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
                    for (Vec2f vec : vectors) {
                        final double x = hopper.getXPos() + vec.x + 0.5;
                        final double y = hopper.getYPos() + 1.0;
                        final double z = hopper.getZPos() + vec.y + 0.5;
                        bufferBuilder.pos(
                                x - mc.getRenderManager().viewerPosX,
                                y - mc.getRenderManager().viewerPosY,
                                z - mc.getRenderManager().viewerPosZ
                        );
                    }
                    tessellator.draw();
                    GlStateManager.color(1f, 1f, 1f, 1f);
                    RenderUtil.releaseGL();
                }
            }
        }
    }
}