package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.KeyPressEvent
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.event.events.RenderWorldLastEvent
import me.gavin.gavhack.misc.RenderUtils
import me.gavin.gavhack.misc.Utils
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.EnumSetting
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.util.AxisAlignedBB
import optifine.BlockPos
import org.lwjgl.input.Keyboard;

object AutoBuild: Module("AutoBuild", "Builds stuff automatically", ModCategory.WORLD) {

    enum class BuildMode {
        FLOOR
    }

    private val modeSetting = addSetting(EnumSetting("Mode", BuildMode.FLOOR))

    private val renderPositions = ArrayList<BlockPos>()

    private var test: BlockPos? = null

    private val tickListener = Listener<PlayerLivingTickEvent> {
        renderPositions.clear()

        val below = BlockPos(
            mc.thePlayer.posX.toInt() + if (mc.thePlayer.posX < 0.0) -1 else 0,
            mc.thePlayer.posY.toInt() - 2,
            mc.thePlayer.posZ.toInt() + if (mc.thePlayer.posZ < 0.0) -1 else 0
        )

        test = BlockPos(below.x + 1, below.y, below.z)

        renderPositions.add(test!!)

        if (mc.theWorld.getBlock(below.x, below.y, below.z).isBlockNormalCube)
            renderPositions.add(below)
    }

    private val keyPress = Listener<KeyPressEvent> { (it as KeyPressEvent)
        if (it.key == Keyboard.KEY_K && test != null) {
            println("sending poop packet")
            Utils.blockPlacePacket(test!!, 1, mc.thePlayer.currentEquippedItem)
        }
    }

    private val renderListener = Listener<RenderWorldLastEvent> { (it as RenderWorldLastEvent)

        RenderUtils.prepare3d()

        for (pos in renderPositions) {
            val x = pos.x.toDouble()
            val y = pos.y.toDouble()
            val z = pos.z.toDouble()
                RenderGlobal.drawOutlinedBoundingBox(
                AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0)
                .offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ),
                0x00ff00
            )
        }

        RenderUtils.release3d()
    }

    init {
        addEventListener(tickListener, PlayerLivingTickEvent::class)
        addEventListener(renderListener, RenderWorldLastEvent::class)
        addEventListener(keyPress, KeyPressEvent::class)
    }

    // automatically finds best enumFacing to place block at, and sends place packet, returns true if successful
    private fun placeBlockAtPos(pos: BlockPos): Boolean {
        for (i in 0..5) {

        }
        return true
    }

}