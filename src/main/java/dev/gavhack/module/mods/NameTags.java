package dev.gavhack.module.mods;

import dev.gavhack.events.RenderNametagEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.ProjectionUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NameTags extends Module {

    private final FloatSetting scale = new FloatSetting("Scale", 0.75f, 0.1f, 2f);

    public NameTags() {
        super("NameTags", "Better player nametags", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.equals(mc.player))
                continue;

            final double posX = MathHelper.clampedLerp(player.lastTickPosX, player.posX, event.getPartialTicks());
            final double posY = MathHelper.clampedLerp(player.lastTickPosY, player.posY, event.getPartialTicks());
            final double posZ = MathHelper.clampedLerp(player.lastTickPosZ, player.posZ, event.getPartialTicks());

            final Vec3d pos = ProjectionUtil.toScaledScreenPos(new Vec3d(posX, posY + player.height + (player.isSneaking() ? -0.2 : 0.25), posZ));

            final String text = player.getName();

            GlStateManager.pushMatrix();
            GlStateManager.translate(pos.x, pos.y, 0);
            GlStateManager.scale(scale.getValue(), scale.getValue(), 0f);
            Gui.drawRect(
                    -((mc.fontRenderer.getStringWidth(text) + 4) / 2),
                    -(mc.fontRenderer.FONT_HEIGHT + 2 ),
                    (mc.fontRenderer.getStringWidth(text) + 4) / 2,
                    1, 0x90000000);
            mc.fontRenderer.drawStringWithShadow(text, -(mc.fontRenderer.getStringWidth(text) / 2.0f), -mc.fontRenderer.FONT_HEIGHT, getNameColor(player));
            GlStateManager.popMatrix();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onNameTagRender(RenderNametagEvent event) {
        event.setCanceled(true);
    }

    private int getNameColor(EntityPlayer player) {
        return player.isSneaking() ? 0xc90000 : -1;
    }
}
