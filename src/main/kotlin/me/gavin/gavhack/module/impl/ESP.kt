package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.misc.MathUtils
import me.gavin.gavhack.misc.ProjectionHelper
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.renderer.ActiveRenderInfo
import net.minecraft.client.renderer.GLAllocation
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.Vec3
import org.lwjgl.util.glu.GLU


object ESP : Module("ESP", "See entities through walls", ModCategory.RENDER) {

    private val frustum = Frustum()

    private val renderOverlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (!isEnabled())
            return@Listener
//
//        frustum.setPosition(mc.renderViewEntity.posX, mc.renderViewEntity.posY, mc.renderViewEntity.posZ)
//
//        for (entity in mc.theWorld.loadedEntityList) {
//            if (entity == mc.renderViewEntity || entity !is EntityLivingBase || !frustum.isBoundingBoxInFrustum(entity.boundingBox))
//                continue
//
//            val worldPos = MathUtils.lerpEntity(entity, it.partialTicks)
//            val screenPos = ProjectionHelper.worldToScreen(worldPos, it.res) ?: continue
//
//            val str = entity.commandSenderName
//            it.font.drawStringWithShadow(str, screenPos.xCoord.toInt() - (it.font.getStringWidth(str) / 2), screenPos.yCoord.toInt() - (it.font.FONT_HEIGHT / 2), -1)
//        }
        
    }

//    private fun getCornerPositions(e: Entity, partialTicks: Float, width: Int, height: Int): FloatArray? {
//        var x = -1f
//        var y = -1f
//        var w = (width + 1).toFloat()
//        var h = (height + 1).toFloat()
//        val pos = MathUtils.lerpEntity(e, partialTicks)
//
//        var bb: AxisAlignedBB = e.getBoundingBox()
//        if (e is EntityEnderCrystal) {
//            bb = AxisAlignedBB.getBoundingBox(bb.minX + 0.3f, bb.minY + 0.2f, bb.minZ + 0.3f, bb.maxX - 0.3f, bb.maxY, bb.maxZ - 0.3f)
//        }
//        if (e is EntityItem) {
//            bb = AxisAlignedBB.getBoundingBox(bb.minX, bb.minY + 0.7f, bb.minZ, bb.maxX, bb.maxY, bb.maxZ)
//        }
//        bb = bb.expand(0.15, 0.1, 0.15)
//        camera.setPosition(mc.renderViewEntity.posX, mc.renderViewEntity.posY, mc.renderViewEntity.posZ)
//        if (!camera.isBoundingBoxInFrustum(bb)) {
//            return null
//        }
//        val corners = arrayOf(
//                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, 0.0, bb.minZ - bb.maxZ + e.width / 2),
//                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, 0.0, bb.minZ - bb.maxZ + e.width / 2),
//                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, 0.0, bb.maxZ - bb.minZ - e.width / 2),
//                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, 0.0, bb.maxZ - bb.minZ - e.width / 2),
//                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
//                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
//                Vec3.createVectorHelper(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2),
//                Vec3.createVectorHelper(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2)
//        )
//        for (vec in corners) {
//            val projection: Pair<Float, Float>? = ProjectionHelper.worldToScreenScaled(Vec3.createVectorHelper(
//                    pos.xCoord + vec.xCoord,
//                    pos.yCoord + vec.yCoord,
//                    pos.zCoord + vec.zCoord), )
//            x = max(x, projection!!.first.toFloat())
//            y = max(y, projection.yCoord.toFloat())
//            w = min(w, projection.xCoord.toFloat())
//            h = min(h, projection.yCoord.toFloat())
//        }
//
//        return if (x != -1f && y != -1f && w != (width + 1).toFloat() && h != (height + 1).toFloat()) {
//            floatArrayOf(x, y, w, h)
//        } else null
//    }

    init {
        EventBus.addListener(renderOverlayEvent, RenderOverlayEvent::class)
    }

}