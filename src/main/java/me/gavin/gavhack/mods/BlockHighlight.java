package me.gavin.gavhack.mods;

import me.gavin.gavhack.misc.RenderUtil;
import me.gavin.gavhack.mods.api.Module;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super("BlockHighlight", "Better block highlight", Category.Render);
    }

    @SubscribeEvent
    public void onDrawHighlight(DrawBlockHighlightEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (shouldRun()) {
            if (mc.objectMouseOver != null) {
                final RayTraceResult result = mc.objectMouseOver;
                if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    GL11.glLineWidth(1f);
                    RenderUtil.prepare3d();
                    final BlockPos pos = result.getBlockPos();
                    final AxisAlignedBB bb = mc.world.getBlockState(pos).getSelectedBoundingBox(mc.world, pos)
                            .offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
                    RenderGlobal.drawSelectionBoundingBox(bb, 1f, 1f, 1f, 1f);
                    RenderUtil.release3d();
                }
            }
        }
    }
}
