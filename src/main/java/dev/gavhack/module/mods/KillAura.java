package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector3f;

import java.util.Comparator;

public class KillAura extends Module {

    private final FloatSetting range = new FloatSetting("Range", 4.0f, 2.0f, 5.0f);

    public KillAura() {
        super("KillAura", "craphack", Category.COMBAT);
    }

    private EntityLivingBase targetEntity = null;

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        if (targetEntity != null) {
            if (!targetCheck(targetEntity)) {
                targetEntity = null;
                return;
            } else {
                if (mc.player.getCooledAttackStrength(0f) >= 1f) {
                    mc.playerController.attackEntity(mc.player, targetEntity);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        } else {
            targetEntity = findTarget();
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (targetEntity != null) {
            final double x = MathHelper.clampedLerp(targetEntity.prevPosX, targetEntity.posX, event.getPartialTicks());
            final double y = MathHelper.clampedLerp(targetEntity.prevPosY, targetEntity.posY, event.getPartialTicks());
            final double z = MathHelper.clampedLerp(targetEntity.prevPosZ, targetEntity.posZ, event.getPartialTicks());
            final double halfWidth = targetEntity.width / 2.0;
            final AxisAlignedBB bb = new AxisAlignedBB(
                    x - halfWidth, y, z - halfWidth,
                    x + halfWidth, y + targetEntity.height, z + halfWidth
            ).grow(0.2).offset(RenderUtil.getRenderOffset());

            Vector3f color = new Vector3f(0f, 1f, 0f);

            if (targetEntity.hurtTime > 0) {
                color.x = 1f;
                color.y = 0f;
            }

            RenderUtil.prepareGL();
            GlStateManager.glLineWidth(1.5f);
            RenderGlobal.drawSelectionBoundingBox(bb, color.x, color.y, color.z, 1f);
            RenderUtil.releaseGL();
        }
    }

    private boolean targetCheck(EntityLivingBase ent) {
        return ent.getDistance(mc.player) <= range.getValue() && ent.isEntityAlive() && ent.getHealth() > 0 && mc.player.getRidingEntity() != ent;
    }

    private EntityLivingBase findTarget() {
        return mc.world.loadedEntityList.stream()
                .filter(e -> !e.equals(mc.player))
                .filter(e -> e instanceof EntityLivingBase)
                .map(e -> (EntityLivingBase) e)
                .filter(this::targetCheck)
                .sorted(Comparator.comparing(e -> e.getDistance(mc.player)))
                .findFirst().orElse(null);
    }
}
