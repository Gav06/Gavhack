package me.gavin.gavhack.module.impl

import com.sun.javafx.geom.Vec3d
import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.RenderOverlayEvent
import me.gavin.gavhack.misc.MathUtils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import net.minecraft.wdl.WDL
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO


object OppDetector: Module("OppDetector", "schitzo esp", ModCategory.RENDER) {

    private var arabTex: ResourceLocation? = null

    private val BOTTLE_PATH = "/assets/minecraft/gavhack/bottle.png"
    private val ARAB_PATH = "/assets/minecraft/gavhack/arab.png"

    private val renderListener = Listener<RenderOverlayEvent> { (it as RenderOverlayEvent)
        if (arabTex != null) {
            val playerX: Double =
                MathUtils.lerpd(mc.thePlayer.lastTickPosX, mc.thePlayer.posX, it.partialTicks.toDouble())
            val playerY: Double =
                MathUtils.lerpd(mc.thePlayer.lastTickPosY, mc.thePlayer.posY, it.partialTicks.toDouble())
            val playerZ: Double =
                MathUtils.lerpd(mc.thePlayer.lastTickPosZ, mc.thePlayer.posZ, it.partialTicks.toDouble())


            camera.setPosition(playerX, playerY, playerZ)
            for (player in WDL.mc.world.playerEntities) {
                if (player == WDL.mc.getRenderViewEntity()) continue

                if (!camera.isBoundingBoxInFrustum(player.getEntityBoundingBox())) continue

                val bottomVec: Vec3d = MathUtil.getLerpPos(player, event.getPartialTicks())
                val topVec: Vec3d = bottomVec.add(Vec3d(0.0, player.getRenderBoundingBox().maxY - player.posY, 0.0))

                val top: Vec3d = ProjectionUtil.toScaledScreenPos(topVec)
                val bottom: Vec3d = ProjectionUtil.toScaledScreenPos(bottomVec)
                val height = bottom.y.toInt() - top.y.toInt()
                val width = height
                val x = (top.x - width / 1.8).toInt()
                val y = top.y.toInt()
                WDL.mc.renderEngine.bindTexture(squid)
                GlStateManager.color(1f, 1f, 1f)
                Gui.drawScaledCustomSizeModalRect(x, y, 0f, 0f, width, height, width, height, width, height)
            }
        } else {
            FMLLog.log.error("Pewb")
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
                tex.loadTexture(WDL.mc.resourceManager)
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