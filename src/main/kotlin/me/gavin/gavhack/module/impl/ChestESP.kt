package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.utilities.RenderUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.tileentity.TileEntityEnderChest
import net.minecraft.util.AxisAlignedBB

object ChestESP: Module("ChestESP", "See chests through walls", ModCategory.RENDER) {

//    private val fadeSetting = addSetting(ToggleSetting("Fade", true))

    val camera = Frustum()

    private val renderListener = Listener<RenderWorldLastEvent> { (it as RenderWorldLastEvent)

        val viewPos = MathUtils.lerpEntity(mc.renderViewEntity, it.partialTicks)
        camera.setPosition(
                viewPos.xCoord, viewPos.yCoord, viewPos.zCoord
        )

        RenderUtils.prepare3d()

        for (tile in mc.renderGlobal.tileEntities) {
            if (tile is TileEntityEnderChest || tile is TileEntityChest) {


                val color = if (tile is TileEntityChest) 0xf99c04 else 0x7443f9

                val aabb = AxisAlignedBB.getBoundingBox(
                    tile.x.toDouble(),
                    tile.y.toDouble(),
                    tile.z.toDouble(),
                    tile.x.toDouble() + 1.0,
                    tile.y.toDouble() + 1.0,
                    tile.z.toDouble() + 1.0
                )


                if (!camera.isBoundingBoxInFrustum(aabb))
                    continue

                RenderGlobal.drawOutlinedBoundingBox(
                    aabb.offset(-RenderManager.instance.viewerPosX, -RenderManager.instance.viewerPosY, -RenderManager.instance.viewerPosZ),
                    color
                )
            }
        }

        RenderUtils.release3d()
    }

    init {
        addEventListener(renderListener, RenderWorldLastEvent::class)
    }
}