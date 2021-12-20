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
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Search extends Module {

    public static final HashSet<Block> desiredBlocks = new HashSet<>();
    private static final ConcurrentHashMap<BlockPos, SearchData> blocks = new ConcurrentHashMap<>();

    private final int renderMesh = GL11.glGenLists(1);
    private static boolean recompile = true;
    private final MilliTimer recompileTimer = new MilliTimer();

    private final FloatSetting regenMeshTimer = new FloatSetting("RegenMeshTime", 500f, 100f, 2000f);

    public Search() {
        super("Search", "Highlighs specific blocks", Category.RENDER);

        desiredBlocks.add(Blocks.PORTAL);
        desiredBlocks.add(Blocks.SPONGE);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketBlockChange) {
            final SPacketBlockChange packet = (SPacketBlockChange) event.getPacket();
            blocks.remove(packet.getBlockPosition());
            recompile = true;
        } else if (event.getPacket() instanceof SPacketMultiBlockChange) {
            final SPacketMultiBlockChange packet = (SPacketMultiBlockChange) event.getPacket();
            boolean changed = false;
            for (int i = 0; i < packet.getChangedBlocks().length; i++) {
                final SPacketMultiBlockChange.BlockUpdateData updateData = packet.getChangedBlocks()[i];
                blocks.remove(updateData.getPos());
                changed = true;
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
            // getting the actual pos
            for (int x = pos.getX(); x > pos.getX() - 16; x--) {
                for (int y = pos.getY(); y > pos.getY() - 16; y--) {
                    for (int z = pos.getZ(); z > pos.getZ() - 16; z--) {
                        final BlockPos bpos = new BlockPos(x, y, z);
                        if (desiredBlocks.contains(mc.world.getBlockState(bpos).getBlock())) {
                            blocks.put(bpos, new SearchData(bpos, event.getState(), 0xff0000));
                        }
                    }
                }
            }
        }
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
