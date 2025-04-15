package me.gavin.gavhack.module.impl.hud

import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.gui.api.Rect
import me.gavin.gavhack.module.HUDModule
import me.gavin.gavhack.utilities.BlockUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.Direction
import net.minecraft.util.EnumChatFormatting

object Position: HUDModule("Position", "Shows your position in the world") {

    private var text: String = ""

    private fun getText(): String {
        val mc = Minecraft.getMinecraft()
        val res = ScaledResolution(mc)

        // Shorten our nums into 1 decimal place
        val x = "%.1f".format(mc.thePlayer.posX)
        val y = "%.1f".format(mc.thePlayer.boundingBox.minY)
        val z = "%.1f".format(mc.thePlayer.posZ)

        val direction = BlockUtil.direction(mc.renderViewEntity.rotationYaw)
        val cardinalDirChar = (if (direction == 2) EnumChatFormatting.RED.toString() else EnumChatFormatting.GRAY.toString()) + Direction.directions[direction][0]

        val quadrantDir = when (direction) {
            0 -> "(+Z)"
            1 -> "(-X)"
            2 -> "(-Z)"
            3 -> "(+X)"
            else -> ""
        }

        var str = "$cardinalDirChar${EnumChatFormatting.GRAY} $quadrantDir${EnumChatFormatting.RESET} $x $y $z"
        val dim = mc.thePlayer.dimension

        // If we aren't in the end dimension
        if (dim != 1) {

            val altX = "%.1f".format(if (dim == 0) mc.thePlayer.posX / 8 else mc.thePlayer.posX * 8)
            val altZ = "%.1f".format(if (dim == 0) mc.thePlayer.posZ / 8 else mc.thePlayer.posZ * 8)

            str += "${EnumChatFormatting.GRAY} $altX $altZ"
        }

        return str
    }

    override fun updateBounds(comp: Rect) {
        comp.setSize(mc.fontRenderer.getStringWidth(text) + 2, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2)
    }

    override fun drawComponent(comp: Rect, mouseX: Int, mouseY: Int, partialTicks: Float) {
        mc.fontRenderer.drawStringWithShadow(text, comp.x + 1, comp.y + 1, -1)
    }

    private val tickListener = Listener<PlayerLivingTickEvent> {
        text = getText()
    }

    init {
        EventBus.addListener(tickListener, PlayerLivingTickEvent::class)
    }
}