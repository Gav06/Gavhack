package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import net.minecraft.block.Block
import net.minecraft.init.Blocks

object Xray: Module("Xray", "See ores and blocks through walls", ModCategory.RENDER) {

    val blockSet = HashSet<Block>()
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

    private fun addBlock(block: Block) {
        blockSet.add(block)
        blockIds.add(Block.getIdFromBlock(block))
    }

    init {
        addBlock(Blocks.diamond_ore)
        addBlock(Blocks.iron_ore)
        addBlock(Blocks.gold_ore)
        addBlock(Blocks.lapis_ore)
        addBlock(Blocks.redstone_ore)
        addBlock(Blocks.lit_redstone_ore)
        addBlock(Blocks.coal_ore)
        addBlock(Blocks.quartz_ore)
        addBlock(Blocks.water)
        addBlock(Blocks.flowing_water)
        addBlock(Blocks.lava)
        addBlock(Blocks.flowing_lava)
    }
}