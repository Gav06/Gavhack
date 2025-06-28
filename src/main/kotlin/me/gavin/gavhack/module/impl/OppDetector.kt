package me.gavin.gavhack.module.impl

import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.utilities.ProjectionHelper
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO


object OppDetector: Module("OppDetector", "schitzo esp", ModCategory.RENDER) {

    private var arabTex: ResourceLocation? = null

    private val BOTTLE_PATH = "/assets/minecraft/gavhack/bottle.png"
    private val ARAB_PATH = "/assets/minecraft/gavhack/arab.png"

    private val camera = Frustum()

    private val renderListener = Listener<RenderOverlayEvent> { (it as RenderOverlayEvent)
        if (arabTex != null) {
            val playerX: Double =
                MathUtils.lerpd(mc.thePlayer.lastTickPosX, mc.thePlayer.posX, it.partialTicks.toDouble())
            val playerY: Double =
                MathUtils.lerpd(mc.thePlayer.lastTickPosY, mc.thePlayer.posY, it.partialTicks.toDouble())
            val playerZ: Double =
                MathUtils.lerpd(mc.thePlayer.lastTickPosZ, mc.thePlayer.posZ, it.partialTicks.toDouble())
            
            camera.setPosition(playerX, playerY, playerZ)
            for (player in mc.theWorld.playerEntities) {
                if (player == mc.renderViewEntity) continue

                if (!camera.isBoundingBoxInFrustum(player.boundingBox)) continue

                val bottomVec: Vec3 = MathUtils.lerpEntity(player, it.partialTicks)
                val topVec: Vec3 = bottomVec.addVector(0.0, player.boundingBox.maxY - player.posY, 0.0)

                val top: Vec3? = ProjectionHelper.worldToScreen(topVec, it.res)
                val bottom: Vec3? = ProjectionHelper.worldToScreen(bottomVec, it.res)

                if (top == null || bottom == null)
                    continue

                val height = bottom.yCoord.toInt() - top.yCoord.toInt()
                val width = height
                val x = (top.xCoord - width / 1.8).toInt()
                val y = top.yCoord.toInt()
                mc.textureManager.bindTexture(arabTex)
                GL11.glColor3f(1f, 1f, 1f)
                Gui.drawModalRectWithCustomSizedTexture(x, y, 0f, 0f, width, height,
                    width.toFloat(), height.toFloat())
            }
        } else {
            Gavhack.log.error("OppDetector image is null")
        }
    }



    init {
        var image: BufferedImage? = null
        try {
            val stream: InputStream? = javaClass.getResourceAsStream(ARAB_PATH)
            if (stream != null) {
                image = ImageIO.read(stream)
            }

            if (image != null) {
                val tex = DynamicTexture(image)
                tex.loadTexture(mc.resourceManager)
                arabTex = mc.textureManager.getDynamicTextureLocation("GAVHACK_ARAB", tex)
            } else {
                Gavhack.log.error("Failed to load image")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        addEventListener(renderListener, RenderOverlayEvent::class)
    }
}