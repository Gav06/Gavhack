package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.block.Block
import net.minecraft.init.Blocks

object Xray: Module("Xray", "See ores and blocks through walls", ModCategory.RENDER) {

    val blockIds = HashSet<Int>();

    override fun onEnable() {
        if (mc.theWorld != null) {
            mc.renderGlobal.loadRenderers()
        }
    }

    override fun onDisable() {
        if (mc.theWorld != null) {
            mc.renderGlobal.loadRenderers()
        }
    }

    init {
        blockIds.add(Block.getIdFromBlock(Blocks.diamond_ore))
        blockIds.add(Block.getIdFromBlock(Blocks.iron_ore))
        blockIds.add(Block.getIdFromBlock(Blocks.gold_ore))
        blockIds.add(Block.getIdFromBlock(Blocks.lapis_ore))
        blockIds.add(Block.getIdFromBlock(Blocks.redstone_ore))
        blockIds.add(Block.getIdFromBlock(Blocks.lit_redstone_ore))
        blockIds.add(Block.getIdFromBlock(Blocks.coal_ore))
        blockIds.add(Block.getIdFromBlock(Blocks.quartz_ore))

    }
}