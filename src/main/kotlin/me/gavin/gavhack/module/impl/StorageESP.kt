package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.misc.MathUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.tileentity.TileEntityEnderChest
import net.minecraft.util.AxisAlignedBB
import org.lwjgl.opengl.GL11

object StorageESP: Module("StorageESP", "See chests through walls", ModCategory.RENDER) {

    val camera = Frustum()

    private val renderListener = Listener<RenderWorldLastEvent> { (it as RenderWorldLastEvent)

        val viewPos = MathUtils.lerpEntity(mc.renderViewEntity, it.partialTicks)
        camera.setPosition(
                viewPos.xCoord, viewPos.yCoord, viewPos.zCoord
        )

        GL11.glPushMatrix()
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST)
        GL11.glLineWidth(1.0f)
        GL11.glTranslated(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ)

        for (tile in mc.renderGlobal.tileEntities) {
            if (tile !is TileEntityChest)
                continue

            val aabb = AxisAlignedBB.getBoundingBox(
                    tile.x.toDouble(), tile.y.toDouble(), tile.z.toDouble(),
                    tile.x + 1.0, tile.y + 1.0, tile.z + 1.0
            )


            if (!camera.isBoundingBoxInFrustum(aabb))
                continue

            RenderGlobal.drawOutlinedBoundingBox(aabb, 0xaaf99c04.toInt())
        }

        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE)
        GL11.glDisable(GL11.GL_LINE_SMOOTH)
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glPopMatrix()
    }

    init {
        addEventListener(renderListener, RenderWorldLastEvent::class)
    }
}