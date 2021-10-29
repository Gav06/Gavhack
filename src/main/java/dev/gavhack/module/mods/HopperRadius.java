package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.MathUtil;
import dev.gavhack.util.RenderUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.math.AxisAlignedBB;
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

    private final double HOPPER_RANGE = 8.0D;

    private final Vec2f[] vectors = MathUtil.getCirclePoints((float) HOPPER_RANGE, 90);

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityHopper) {
                final TileEntityHopper hopper = (TileEntityHopper) tileEntity;
                if (tileEntity.getPos().getDistance((int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ) <= renderRange.getValue()) {
                    RenderUtil.prepareGL();
                    GlStateManager.glLineWidth(1.5f);
                    if (mc.player.getDistance(hopper.getXPos(), hopper.getYPos(), hopper.getZPos()) > HOPPER_RANGE) {
                        GlStateManager.color(1f, 0f, 0f, 1f);
                    } else {
                        GlStateManager.color(0f, 1f, 0f, 1f);
                    }
                    final Tessellator tessellator = Tessellator.getInstance();
                    final BufferBuilder bufferBuilder = tessellator.getBuffer();
                    bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
                    for (Vec2f vec : vectors) {
                        final double x = hopper.getXPos() + vec.x;
                        final double y = hopper.getYPos() + 0.5;
                        final double z = hopper.getZPos() + vec.y;
                        bufferBuilder.pos(
                                x - mc.getRenderManager().viewerPosX,
                                y - mc.getRenderManager().viewerPosY,
                                z - mc.getRenderManager().viewerPosZ
                        ).endVertex();
                    }
                    tessellator.draw();
//                    RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(hopper.getPos()).offset(RenderUtil.getRenderOffset()), 1f, 0f, 0f, 1f);
                    GlStateManager.color(1f, 1f, 1f, 1f);
                    RenderUtil.releaseGL();
                }
            }
        }
    }
}