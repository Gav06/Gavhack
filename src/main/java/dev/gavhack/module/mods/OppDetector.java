package dev.gavhack.module.mods;

import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.util.MathUtil;
import dev.gavhack.util.ProjectionUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public final class OppDetector extends Module {

    private final ICamera camera = new Frustum();
    private final String IMG_PATH = "/assets/gavhack/img/romanian.png";
    private ResourceLocation squid = null;

    public OppDetector() {
        super("OppDetector", "o no", Category.RENDER);

        BufferedImage image = null;
        try {
            final InputStream stream = getClass().getResourceAsStream(IMG_PATH);
            if (stream != null) {
                image = ImageIO.read(stream);
            }

            if (image != null) {
                final DynamicTexture tex = new DynamicTexture(image);
                tex.loadTexture(mc.getResourceManager());
                squid = mc.getTextureManager().getDynamicTextureLocation("GAVHACK_SQUIDWARD", tex);
            } else {
                FMLLog.log.error("Failed to load romanian squidward");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (squid != null) {
            final double playerX = MathHelper.clampedLerp(mc.player.lastTickPosX, mc.player.posX, event.getPartialTicks());
            final double playerY = MathHelper.clampedLerp(mc.player.lastTickPosY, mc.player.posY, event.getPartialTicks());
            final double playerZ = MathHelper.clampedLerp(mc.player.lastTickPosZ, mc.player.posZ, event.getPartialTicks());

            camera.setPosition(playerX, playerY, playerZ);
            for (EntityPlayer player : mc.world.playerEntities) {
                if (player.equals(mc.getRenderViewEntity()))
                    continue;

                if (!camera.isBoundingBoxInFrustum(player.getEntityBoundingBox()))
                    continue;

                final Vec3d bottomVec = MathUtil.getLerpPos(player, event.getPartialTicks());
                final Vec3d topVec = bottomVec.add(new Vec3d(0.0, player.getRenderBoundingBox().maxY - player.posY, 0.0));

                final Vec3d top = ProjectionUtil.toScaledScreenPos(topVec);
                final Vec3d bottom = ProjectionUtil.toScaledScreenPos(bottomVec);

                final int height;
                final int width = height = (int) bottom.y - (int) top.y;
                final int x = (int) (top.x - width / 1.8);
                final int y = (int) top.y;
                mc.renderEngine.bindTexture(squid);
                GlStateManager.color(1f, 1f, 1f);
                Gui.drawScaledCustomSizeModalRect(x, y, 0f, 0f, width, height, width, height, width, height);
            }
        } else {
            FMLLog.log.error("Pewb");
        }
    }
}