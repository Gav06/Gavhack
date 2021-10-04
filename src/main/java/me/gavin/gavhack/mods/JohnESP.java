//package me.gavin.gavhack.mods;
//
//import me.gavin.gavhack.Gavhack;
//import me.gavin.gavhack.misc.ProjectionUtil;
//import me.gavin.gavhack.misc.RenderUtil;
//import me.gavin.gavhack.mods.api.Module;
//import net.minecraft.client.gui.Gui;
//import net.minecraft.client.gui.ScaledResolution;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3d;
//import net.minecraftforge.client.event.RenderGameOverlayEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class JohnESP extends Module {
//    public JohnESP() {
//        super("JohnESP", "\uD83D\uDC4D", Category.Render);
//    }
//
//    private final ResourceLocation texture = new ResourceLocation(Gavhack.ID,"textures/john.jpg");
//
//    @SubscribeEvent
//    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
//        final ScaledResolution sr = new ScaledResolution(mc);
//        for (Entity entity : mc.world.loadedEntityList) {
//            if (mc.player.equals(entity))
//                continue;
//            if (entity instanceof EntityLivingBase) {
//                final Vec3d bottomVec = new Vec3d(
//                        MathHelper.clampedLerp(entity.lastTickPosX, entity.posX, event.getPartialTicks()),
//                        MathHelper.clampedLerp(entity.lastTickPosX, entity.posX, event.getPartialTicks()),
//                        MathHelper.clampedLerp(entity.lastTickPosX, entity.posX, event.getPartialTicks()));
//                final Vec3d topVec = bottomVec.add(0.0, entity.getRenderBoundingBox().maxY - entity.posY,0.0);
//
//                Vec3d top = ProjectionUtil.toScreenPos(topVec);
//                Vec3d bottom = ProjectionUtil.toScreenPos(bottomVec);
//
//                int height = (int) (bottom.y - top.y);
//                int width = height;
//
//                int x = (int) (top.x - (width / 1.8));
//                int y = (int) top.y;
//
//                mc.renderEngine.bindTexture(texture);
//
//                GlStateManager.color(1f, 1f, 1f, 1f);
//                Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
//            }
//        }
//    }
//}
