package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.ToggleSetting
import me.gavin.gavhack.utilities.FrameBufferEffect
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.utilities.ProjectionHelper
import me.gavin.gavhack.utilities.RenderUtils
import me.gavin.gavhack.utilities.shaders.DefaultShaderEffect
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityEnderCrystal
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Vec3
import net.minecraft.wdl.WDL
import org.lwjgl.opengl.GL11


object ESP : Module("ESP", "See entities through walls", ModCategory.RENDER) {

    enum class Mode {
        CSGO,
        SHADER
    }

    val mode = addSetting(EnumSetting("Mode", Mode.CSGO))

    val players = addSetting(ToggleSetting("Players", true))
    val hostiles = addSetting(ToggleSetting("Hostiles", true))
    val passive = addSetting(ToggleSetting("Animals", true))
    val other = addSetting(ToggleSetting("Others", false))

    private val defaultshader = FrameBufferEffect(DefaultShaderEffect(), mc.displayWidth, mc.displayHeight)

    private val frustum = Frustum()

    private val renderOverlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (mode.enumValue() == Mode.CSGO) {
            drawCSGOEsp(it)
        } else if (mode.enumValue() == Mode.SHADER) {
            drawShaderESP(it)
        }
    }

    private fun drawShaderESP(it: RenderOverlayEvent) {
        //
        defaultshader.resize(mc.displayWidth, mc.displayHeight)

        defaultshader.beginRender()
        for (e in mc.theWorld.loadedEntityList) {
            if (e == mc.thePlayer) continue

            RenderManager.instance.renderEntityStatic(e, it.partialTicks, true)
        }

        defaultshader.endRender()
    }

    private fun drawCSGOEsp(it: RenderOverlayEvent) {
        frustum.setPosition(mc.renderViewEntity.posX, mc.renderViewEntity.posY, mc.renderViewEntity.posZ)

        for (entity in mc.theWorld.loadedEntityList) {
            if (entity == mc.renderViewEntity || entity == null)
                continue

            if (!frustum.isBoundingBoxInFrustum(entity.boundingBox))
                continue

            if (!entityCheck(entity))
                continue

            val corners = getCornerPositions(entity, it.partialTicks, it.res) ?: continue
            GL11.glLineWidth(2.0f)
            RenderUtils.outlineRect(corners[0], corners[1], corners[2], corners[3], 0xff000000.toInt())
            GL11.glLineWidth(1.0f)
            RenderUtils.outlineRect(corners[0], corners[1], corners[2], corners[3], 0xffff0000.toInt())

            GL11.glPushMatrix()
            GL11.glTranslatef(corners[2], corners[3] - 5f, 0.0f)
            GL11.glScalef(0.5f, 0.5f, 1.0f)
            it.font.drawStringWithShadow(entity.commandSenderName, 0, 0, -1)
            GL11.glPopMatrix()
        }
    }

    private fun entityCheck(entity: Entity): Boolean {
        return when (entity) {
            is EntityPlayer -> players.value
            is EntityAnimal -> passive.value
            is EntityMob -> hostiles.value
            else -> other.value
        }
    }

    private fun getCornerPositions(e: Entity, partialTicks: Float, res: ScaledResolution): FloatArray? {
        // Start with +- 1.0f out of bounds
        var x = -1f
        var y = -1f
        var w = (res.scaledWidth + 1).toFloat()
        var h = (res.scaledHeight + 1).toFloat()

        // Entity pos
        val pos = MathUtils.lerpEntity(e, partialTicks)
        // Get AABB
        var bb: AxisAlignedBB = e.boundingBox ?: return null

        // Expand if crystal or item
        if (e is EntityEnderCrystal) {
            bb = AxisAlignedBB.getBoundingBox(bb.minX + 0.3f, bb.minY + 0.2f, bb.minZ + 0.3f, bb.maxX - 0.3f, bb.maxY, bb.maxZ - 0.3f)
        }
        if (e is EntityItem) {
            bb = AxisAlignedBB.getBoundingBox(bb.minX, bb.minY + 0.7f, bb.minZ, bb.maxX, bb.maxY, bb.maxZ)
        }
        // Expand more anyways
        bb = bb.expand(0.05, 0.10, 0.05)

        // Every corner
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
        // Loop through each corner
        for (vec in corners) {
            // Project position
            val projection = ProjectionHelper.worldToScreen(Vec3.createVectorHelper(
                    pos.xCoord + vec.xCoord,
                    pos.yCoord + vec.yCoord,
                    pos.zCoord + vec.zCoord), res) ?: return null

            // Find lowest minX and minY
            x = x.coerceAtLeast(projection.xCoord.toFloat())
            y = y.coerceAtLeast(projection.yCoord.toFloat())
            // Find highest maxX and maxY
            w = w.coerceAtMost(projection.xCoord.toFloat())
            h = h.coerceAtMost(projection.yCoord.toFloat())
        }

        return if (x != -1f && y != -1f && w != (res.scaledWidth + 1).toFloat() && h != (res.scaledHeight + 1).toFloat()) {
            floatArrayOf(x, y, w, h)
        } else null
    }

    init {
        addEventListener(renderOverlayEvent, RenderOverlayEvent::class)
    }

}