package dev.gavhack.module.mods;

import dev.gavhack.events.BlockRenderEvent;
import dev.gavhack.events.PacketEvent;
import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.MathUtil;
import dev.gavhack.util.MilliTimer;
import dev.gavhack.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// this search is funni but also kinda retarded
public class Search extends Module {

    public static final HashSet<Block> desiredBlocks = new HashSet<>();
    private static final ConcurrentHashMap<BlockPos, SearchData> blocks = new ConcurrentHashMap<>();
    private static final HashMap<Block, Integer> defaultColors = new HashMap<>();

    static {
        defaultColors.put(Blocks.PORTAL, 0x512bab);
        defaultColors.put(Blocks.SPONGE, 0xede500);
        defaultColors.put(Blocks.END_PORTAL_FRAME, 0x00eda2);
    }

    private final int renderMesh = GL11.glGenLists(1);
    private static boolean recompile = true;
    private final MilliTimer recompileTimer = new MilliTimer();

    private final FloatSetting regenMeshTimer = new FloatSetting("RefreshTime", 2000f, 100f, 5000f);

    public Search() {
        super("Search", "Highlights specific blocks", Category.RENDER);

        desiredBlocks.add(Blocks.PORTAL);
        desiredBlocks.add(Blocks.SPONGE);
        desiredBlocks.add(Blocks.END_PORTAL_FRAME);
    }

    @Override
    protected void onEnable() {
        if (mc.renderGlobal != null) {
            mc.renderGlobal.loadRenderers();
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketBlockChange) {
            final SPacketBlockChange packet = (SPacketBlockChange) event.getPacket();
            if (!desiredBlocks.contains(packet.getBlockState().getBlock())) {
                blocks.remove(packet.getBlockPosition());
                recompile = true;
            }
        } else if (event.getPacket() instanceof SPacketMultiBlockChange) {
            final SPacketMultiBlockChange packet = (SPacketMultiBlockChange) event.getPacket();
            boolean changed = false;
            for (int i = 0; i < packet.getChangedBlocks().length; i++) {
                final SPacketMultiBlockChange.BlockUpdateData updateData = packet.getChangedBlocks()[i];
                if (!desiredBlocks.contains(updateData.getBlockState().getBlock())) {
                    blocks.remove(updateData.getPos());
                    changed = true;
                }
            }

            recompile = changed;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        blocks.clear();
        recompile = true;
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (recompileTimer.hasPassed((long) regenMeshTimer.getValue())) {
            recompileTimer.reset();
            recompile = true;
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {

        if (recompile) {
            GL11.glNewList(renderMesh, GL11.GL_COMPILE);
            for (Map.Entry<BlockPos, SearchData> entry : blocks.entrySet()) {
                final float[] colors = MathUtil.hexToRGB(entry.getValue().getColor());
                GL11.glLineWidth(1f);
                RenderUtil.drawImmediateBox(new AxisAlignedBB(entry.getKey()), colors[0], colors[1], colors[2]);
            }
            GL11.glEndList();

            recompile = false;
        }

        RenderUtil.prepareGL();
        GlStateManager.translate(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
        GL11.glCallList(renderMesh);
        RenderUtil.releaseGL();
    }

    // Called when a section of 16x16x16 blocks is updated
    @SubscribeEvent
    public void onBlockRender(BlockRenderEvent event) {
        final BlockPos pos = event.getPos();

        if (mc.player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) > 65536)
            return;

        if (event.getState().getBlock() != Blocks.AIR && desiredBlocks.contains(event.getState().getBlock())) {
            // getting the actual pos within the chunk of blocks
            for (int x = pos.getX(); x > pos.getX() - 16; x--) {
                for (int y = pos.getY(); y > pos.getY() - 16; y--) {
                    for (int z = pos.getZ(); z > pos.getZ() - 16; z--) {
                        final BlockPos bpos = new BlockPos(x, y, z);
                        final Block block = mc.world.getBlockState(bpos).getBlock();
                        if (desiredBlocks.contains(block)) {
                            blocks.put(bpos, new SearchData(bpos, event.getState(), getColor(block)));
                            recompile = true;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        blocks.entrySet().removeIf(e -> !mc.world.isBlockLoaded(e.getKey(), false));
    }

    @Override
    public String getExtraInfo() {
        return "[" + blocks.size() + "]";
    }

    private int getColor(Block block) {
        int color = 0x5c5c5c;
        if (defaultColors.containsKey(block))
            color = defaultColors.get(block);
        return color;
    }

    public static class SearchData {
        private final BlockPos pos;
        private final IBlockState state;
        private final int color;

        public SearchData(BlockPos pos, IBlockState state, int color) {
            this.pos = pos;
            this.state = state;
            this.color = color;
        }

        public BlockPos getPos() {
            return pos;
        }

        public IBlockState getState() {
            return state;
        }

        public int getColor() {
            return color;
        }
    }
}
