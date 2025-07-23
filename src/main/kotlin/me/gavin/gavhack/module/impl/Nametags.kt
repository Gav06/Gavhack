package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.EntityNametagRenderEvent
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.utilities.MathUtils
import me.gavin.gavhack.utilities.RenderUtils
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11

object Nametags: Module("Nametags", "Better player name tags", ModCategory.RENDER) {

    private val renderListener = Listener<RenderWorldLastEvent> { it as RenderWorldLastEvent

        RenderUtils.prepare3d()
        GL11.glEnable(GL11.GL_TEXTURE_2D)

        for (player in mc.theWorld.playerEntities) {
            if (player == mc.thePlayer) continue

            val lerpPos = MathUtils.lerpEntity(player, it.partialTicks).addVector(0.0, player.height + 0.75, 0.0)

            val flipPitch = if (mc.gameSettings.thirdPersonView == 2) -1 else 1

            GL11.glPushMatrix()
            GL11.glTranslated(lerpPos.xCoord - RenderManager.renderPosX, lerpPos.yCoord - RenderManager.renderPosY, lerpPos.zCoord - RenderManager.renderPosZ)
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0f, 1f, 0f) // Rotate around Y axis, to always face player
            GL11.glRotatef(RenderManager.instance.playerViewX * flipPitch, 1.0f, 0.0f, 0.0f)  // Counteract camera pitch (X-axis rotation)


            val dist = MathUtils.distance3d(lerpPos, Vec3.createVectorHelper(
                mc.renderViewEntity.posX,
                mc.renderViewEntity.posY + mc.renderViewEntity.eyeHeight,
                mc.renderViewEntity.posZ
            ))

            // Scaling formula taken from gamesense because I like how simple and clean it is
            var scale = 0.0018 + 0.003 * dist;
            if (dist <= 8.0) scale = 0.0245;

            GL11.glScaled(-scale, -scale, scale)
            val text = player.commandSenderName
            val texWidth = mc.fontRenderer.getStringWidth(text)

            GL11.glTranslated(-(texWidth / 2.0), 0.0, 0.0)

            mc.fontRenderer.drawStringWithShadow(text, 0, -2, -1)
            GL11.glPopMatrix()
        }

        RenderUtils.release3d()
    }

    private val tagRenderEvent = Listener<EntityNametagRenderEvent> { it as EntityNametagRenderEvent
        if (it.entity is EntityPlayer) {
            it.cancelled = true
        }
    }

    init {
        addEventListener(renderListener, RenderWorldLastEvent::class)
        addEventListener(tagRenderEvent, EntityNametagRenderEvent::class)
    }
}