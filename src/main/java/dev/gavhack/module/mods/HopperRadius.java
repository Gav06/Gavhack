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
                if (tileEntity.getPos().getDistance((int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ) <= renderRange.getValue()) {
                    final Tessellator tessellator = Tessellator.getInstance();
                    final BufferBuilder bufferBuilder = tessellator.getBuffer();

                    RenderUtil.prepareGL();
                    GlStateManager.color(1f, 0f, 0f, 1f);
                    bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
                    for (Vec2f vec : vectors) {
                        bufferBuilder.pos(vec.x + tileEntity.getPos().getX() + 0.5d, tileEntity.getPos().getY() + 1.0, vec.y + tileEntity.getPos().getZ() + 0.5d);
                    }
                    tessellator.draw();
                    GlStateManager.color(1f, 1f, 1f, 1f);
                    RenderUtil.releaseGL();
                }
            }
        }
    }
}