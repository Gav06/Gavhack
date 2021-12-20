package dev.gavhack.module.mods;

import dev.gavhack.events.PacketEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.FloatSetting;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector2f;

import java.util.Set;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glCallList;

public class NewChunks extends Module {

    private final FloatSetting lineWidth = new FloatSetting("LineWidth", 1f, 1f, 4f);
    private final FloatSetting height = new FloatSetting("Y-Level", 0f, 0f, 256f);

    public NewChunks() {
        super("NewChunks", "Highlights newly generated chunks", Category.RENDER);
    }

    private int glList = glGenLists(1);
    private boolean needsUpdate = true;
    private final Set<Vector2f> chunks = new ConcurrentSet<>();

    @Override
    protected void onDisable() {
        chunks.clear();
    }

    @SubscribeEvent
    public void onPacketRead(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChunkData) {
            final SPacketChunkData data = (SPacketChunkData) event.getPacket();

            if (!data.isFullChunk()) {
                final Vector2f vec = new Vector2f(data.getChunkX() * 16, data.getChunkZ() * 16);
                if (!chunks.contains(vec)) {
                    chunks.add(vec);
                    needsUpdate = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        if (needsUpdate) {
            glNewList(glList, GL_COMPILE);
            glPushMatrix();
            for (Vector2f vec : chunks) {
                glBegin(GL_LINE_LOOP);
                glVertex3d(vec.x, 0.0, vec.y);
                glVertex3d(vec.x + 16.0, 0.0, vec.y);
                glVertex3d(vec.x + 16.0, 0.0, vec.y + 16.0);
                glVertex3d(vec.x, 0.0, vec.y + 16.0);
                glEnd();
            }
            glPopMatrix();
            glEndList();

            needsUpdate = false;
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.color(1f, 0f, 0f, 1f);
        glTranslated(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
        glTranslatef(0f, height.getValue(), 0f);
        glLineWidth(lineWidth.getValue());
        glCallList(glList);
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
