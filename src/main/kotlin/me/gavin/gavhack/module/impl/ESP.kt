package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.misc.MathUtils
import me.gavin.gavhack.misc.ProjectionHelper
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityEnderCrystal
import net.minecraft.entity.item.EntityItem
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Vec3


object ESP : Module("ESP", "See entities through walls", ModCategory.RENDER) {

    private val frustum = Frustum()

    private val renderOverlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (!isEnabled())
            return@Listener

        frustum.setPosition(mc.renderViewEntity.posX, mc.renderViewEntity.posY, mc.renderViewEntity.posZ)

        for (entity in mc.theWorld.loadedEntityList) {
            if (entity == mc.renderViewEntity || entity !is EntityLivingBase || !frustum.isBoundingBoxInFrustum(entity.boundingBox))
                continue

            val corners = getCornerPositions(entity, it.partialTicks, it.res) ?: continue

            Gui.drawRect(corners[0].toInt(), corners[1].toInt(), corners[2].toInt(), corners[3].toInt(), 0x60ffffff)
        }

    }

    private fun getCornerPositions(e: Entity, partialTicks: Float, res: ScaledResolution): FloatArray? {
        var x = -1f
        var y = -1f
        var w = (res.scaledWidth + 1).toFloat()
        var h = (res.scaledHeight + 1).toFloat()
        val pos = MathUtils.lerpEntity(e, partialTicks)

        var bb: AxisAlignedBB = e.boundingBox ?: return null

        if (e is EntityEnderCrystal) {
            bb = AxisAlignedBB.getBoundingBox(bb.minX + 0.3f, bb.minY + 0.2f, bb.minZ + 0.3f, bb.maxX - 0.3f, bb.maxY, bb.maxZ - 0.3f)
        }
        if (e is EntityItem) {
            bb = AxisAlignedBB.getBoundingBox(bb.minX, bb.minY + 0.7f, bb.minZ, bb.maxX, bb.maxY, bb.maxZ)
        }
        bb = bb.expand(0.15, 0.1, 0.15)

        val corners = arrayOf(
                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, 0.0, bb.minZ - bb.maxZ + e.width / 2),
                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, 0.0, bb.minZ - bb.maxZ + e.width / 2),
                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, 0.0, bb.maxZ - bb.minZ - e.width / 2),
                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, 0.0, bb.maxZ - bb.minZ - e.width / 2),
                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2),
                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2)
        )
        for (vec in corners) {
            val projection = ProjectionHelper.worldToScreen(Vec3.createVectorHelper(
                    pos.xCoord + vec.xCoord,
                    pos.yCoord + vec.yCoord,
                    pos.zCoord + vec.zCoord), res) ?: return null

            x = x.coerceAtLeast(projection.xCoord.toFloat())
            y = y.coerceAtLeast(projection.yCoord.toFloat())
            w = w.coerceAtMost(projection.xCoord.toFloat())
            h = h.coerceAtMost(projection.yCoord.toFloat())
        }

        return if (x != -1f && y != -1f && w != (res.scaledWidth + 1).toFloat() && h != (res.scaledHeight + 1).toFloat()) {
            floatArrayOf(x, y, w, h)
        } else null
    }

    init {
        EventBus.addListener(renderOverlayEvent, RenderOverlayEvent::class)
    }

}