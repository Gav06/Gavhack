package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.gui.api.Visibility
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.BooleanSetting
import me.gavin.gavhack.utilities.FramebufferEffect
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.utilities.ProjectionHelper
import me.gavin.gavhack.utilities.RenderUtils
import me.gavin.gavhack.utilities.shaders.ESPColorShader
import me.gavin.gavhack.utilities.shaders.OutlineShader
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityEnderCrystal
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.passive.EntitySquid
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.tileentity.TileEntityEnderChest
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.Vector3f


object ESP : Module("ESP", "See entities through walls", ModCategory.RENDER) {

    enum class Mode {
        CSGO,
        SHADER,
        BOX
    }

    val mode = addSetting(EnumSetting("Mode", Mode.CSGO))

    val fill = addSetting(BooleanSetting("Fill", false) { mode.enumValue() == Mode.SHADER })
    val dots = addSetting(BooleanSetting("Dots", false) { mode.enumValue() == Mode.SHADER })

    val players = addSetting(BooleanSetting("Players", true))
    val hostiles = addSetting(BooleanSetting("Hostiles", true))
    val passive = addSetting(BooleanSetting("Animals", true))
    val items = addSetting(BooleanSetting("Items", true))
    val other = addSetting(BooleanSetting("Others", false))

    // buffer for coloring entities how we want them
    private var colorFramebuffer = FramebufferEffect(ESPColorShader(), mc.displayWidth, mc.displayHeight, true)
    // shader for drawing outlines of our already-colored entities. Note: this will draw an outline using the color of the
    // current fragment, a "universal" color uniform is not passed to this.
    private val outlineShader = OutlineShader()
    private val frustum = Frustum()

    private val renderOverlayEvent = Listener<RenderOverlayEvent> { it as RenderOverlayEvent
        if (mc.theWorld == null || TileEntityRendererDispatcher.instance.world == null)
            return@Listener

        frustum.setPosition(mc.renderViewEntity.posX, mc.renderViewEntity.posY, mc.renderViewEntity.posZ)

        if (mode.enumValue() == Mode.CSGO) {
            drawCSGOEsp(it)
        } else if (mode.enumValue() == Mode.SHADER) {
            drawShaderESP(it)
        }
    }

    private fun drawShaderESP(it: RenderOverlayEvent) {
        // prepare render
        GL11.glPushMatrix()
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT or GL11.GL_LIGHTING_BIT)
        RenderUtils.shouldCancelShadows = true
        RenderUtils.shouldCancelNameTagRender = true
        RenderUtils.shouldCancelEnchant = true
        mc.entityRenderer.disableLightmap(0.0);
        RenderHelper.disableStandardItemLighting();

        // create color framebuffer
        colorFramebuffer.updateFramebuffer(mc.displayWidth, mc.displayHeight)
        colorFramebuffer.bindBuffer()
        buildFrameBuffer(it.partialTicks)
        colorFramebuffer.unbindBuffer()

        // bind mc framebuffer
        mc.framebuffer.bindFramebuffer(true)
        mc.entityRenderer.setupOverlayRendering()

        // draw outline shader
        drawOutlineShader(it.res)

        // restore gl state
        mc.entityRenderer.enableLightmap(0.0);
        RenderUtils.shouldCancelShadows = false
        RenderUtils.shouldCancelNameTagRender = false
        RenderUtils.shouldCancelEnchant = false
        GL11.glPopAttrib()
        GL11.glPopMatrix()
        mc.entityRenderer.setupOverlayRendering()
    }

    private fun drawOutlineShader(res: ScaledResolution) {
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glDisable(GL11.GL_LIGHTING)

//        colorFramebuffer.drawFullscreenTexQuad(res)


        outlineShader.startShader()
        outlineShader.updateUniforms()

        // draw quad over whole screen
        colorFramebuffer.drawFullscreenTexQuad(res)

        // finish outline shader
        outlineShader.stopShader()
    }

    private fun buildFrameBuffer(partialTicks: Float) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glDisable(GL11.GL_BLEND)


        mc.entityRenderer.setupCameraTransform(partialTicks, 0)

        colorFramebuffer.shader.startShader()
        val uniform = colorFramebuffer.shader.getUniform("rgb")

        for (e in mc.theWorld.loadedEntityList) {
            if (!frustum.isBoundingBoxInFrustum(e.boundingBox)) continue

            if ((e == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) || !entityCheck(e)) continue

            val color = entityColor(e)
            GL20.glUniform3f(uniform, color.x, color.y, color.z)

            RenderManager.instance.renderEntityStatic(e, partialTicks, true)
        }

        RenderUtils.shouldCancelTileDistanceCheck = true

        // make a copy of the tileEntity list to prevent any weird concurrency issues
        for (tile in ArrayList<TileEntity>(mc.theWorld.tileEntities)) {

            val aabb = AxisAlignedBB.getBoundingBox(
                tile.x.toDouble(), tile.y.toDouble(), tile.z.toDouble(),
                tile.x + 1.0, tile.y + 1.0, tile.z + 1.0
            )

            if (!frustum.isBoundingBoxInFrustum(aabb)) continue


            if (tile is TileEntityChest) {
                GL20.glUniform3f(uniform, 0.9764706f, 0.6117647f, 0.015686275f)
                TileEntityRendererDispatcher.instance.renderTileEntity(tile, partialTicks)
            } else if (tile is TileEntityEnderChest) {
                GL20.glUniform3f(uniform, 0.45490196f, 0.2627451f, 0.9764706f)
                TileEntityRendererDispatcher.instance.renderTileEntity(tile, partialTicks)

            }
        }

        RenderUtils.shouldCancelTileDistanceCheck = false

        colorFramebuffer.shader.stopShader()
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
            is EntityVillager -> passive.value
            is IMob -> hostiles.value
            is EntityItem -> items.value
            else -> other.value
        }
    }

    private fun entityColor(entity: Entity): Vector3f {
        return when (entity) {
            is EntityPlayer -> Vector3f(0.0f, 0.75f, 1f)
            is EntityAnimal -> Vector3f(0f, 1f, 0f)
            is EntityVillager -> Vector3f(0f, 1f, 0f)
            is EntitySquid -> Vector3f(0f, 1f, 0f)
            is IMob -> Vector3f(1f, 0f, 0f)
            is EntityItem -> Vector3f(0f, 1f, 0f)
            else -> Vector3f(0f, 1f, 0f)
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