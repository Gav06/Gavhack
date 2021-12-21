package dev.gavhack.module.mods;

import dev.gavhack.events.RenderModelEvent;
import dev.gavhack.events.RenderNametagEvent;
import dev.gavhack.mixin.accessors.IMixinRender;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.OutlineUtil;
import dev.gavhack.util.shader.FramebufferShader;
import dev.gavhack.util.shader.shaders.DotShader;
import dev.gavhack.util.shader.shaders.OutlineShader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ESP extends Module {

    public static final EnumSetting<EspMode> espMode = new EnumSetting<>("Mode", EspMode.Shader);
    private final EnumSetting<EspShader> shaderMode = new EnumSetting<>("Shader", EspShader.Outline);

    private final FloatSetting lineWidth = new FloatSetting("LineWidth", 2f, 1f, 5f);
    private final FloatSetting red = new FloatSetting("Red", 0f, 0f, 1f);
    private final FloatSetting green = new FloatSetting("Green", 1f, 0f, 1f);
    private final FloatSetting blue = new FloatSetting("Blue", 0f, 0f, 1f);

    private final FloatSetting shaderRadius = new FloatSetting("ShaderRadius", 1f, 0.1f, 4f);
    private final FloatSetting shaderQuality = new FloatSetting("ShaderQuality", 1f, 0.1f, 5f);

    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting animals = new BooleanSetting("Animals", true);
    private final BooleanSetting mobs = new BooleanSetting("Mobs", true);
    private final BooleanSetting other = new BooleanSetting("Other", false);

    public ESP() {
        super("ESP", "highlight specific entities", Category.RENDER);
    }

    private final FramebufferShader outlineShader = new OutlineShader();
    private final FramebufferShader dotShader = new DotShader();

    private boolean shouldRenderNametags = true;

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event) {
        if (espMode.getValue() != EspMode.Shader)
            return;

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            FramebufferShader shader = shaderMode.getValue() == EspShader.Outline ? outlineShader : dotShader;

            shouldRenderNametags = false;
            shader.radius = shaderRadius.getValue();
            shader.quality = shaderQuality.getValue();
            shader.red = red.getValue();
            shader.green = green.getValue();
            shader.blue = blue.getValue();
            shader.startDraw(event.getPartialTicks());
            for (Entity e : mc.world.loadedEntityList) {
                if (e.equals(mc.player) || !shouldEsp(e))
                    continue;

                mc.getRenderManager().renderEntityStatic(e, event.getPartialTicks(), true);
            }
            shader.stopDraw();

            shouldRenderNametags = true;
        }
    }

    @SubscribeEvent
    public void onRenderNametag(RenderNametagEvent event) {
        event.setCanceled(!shouldRenderNametags);
    }

    @SubscribeEvent
    public void onModelRender(RenderModelEvent.Pre<Entity> event) {
        if (espMode.getValue() != EspMode.Outline)
            return;

        if (event.getEntity() == mc.player)
            return;

        if (shouldEsp(event.getEntity())) {

            boolean flag = !event.getEntity().isInvisible();
            boolean flag1 = !flag && !event.getEntity().isInvisibleToPlayer(mc.player);

            if (flag || flag1) {
                // TODO: maybe make this less autistic
                if (!((IMixinRender<Entity>)(mc.getRenderManager().getEntityRenderObject(event.getEntity())))
                        .Render_bindEntityTexture(event.getEntity())) {
                    return;
                }

                if (flag1) {
                    GlStateManager.pushMatrix();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(770, 771);
                    GlStateManager.alphaFunc(516, 0.003921569F);
                }

                final Color clr = new Color(red.getValue(), green.getValue(), blue.getValue());
                GlStateManager.color(1f, 1f, 1f, 1f);
                event.render();
                OutlineUtil.renderOne(lineWidth.getValue());
                event.render();
                OutlineUtil.renderTwo();
                event.render();
                OutlineUtil.renderThree();
                OutlineUtil.renderFour(clr);
                event.render();
                OutlineUtil.renderFive();

                if (flag1) {
                    GlStateManager.disableBlend();
                    GlStateManager.alphaFunc(516, 0.1F);
                    GlStateManager.popMatrix();
                    GlStateManager.depthMask(true);
                }
            }
        }
    }

    private boolean shouldEsp(Entity entity) {
        if (entity instanceof EntityLivingBase || entity instanceof EntityEnderCrystal) {
            if (entity instanceof EntityPlayer) {
                return players.isEnabled();
            } else if (entity instanceof EntityAnimal) {
                return animals.isEnabled();
            } else if (entity instanceof EntityMob) {
                return mobs.isEnabled();
            } else {
                return other.isEnabled();
            }
        } else {
            return false;
        }
    }

    @Override
    public String getExtraInfo() {
        return "[" + espMode.getValue().name() + "]";
    }

    public enum EspMode {
        Shader,
        Outline,
        Box
    }

    public enum EspShader {
        Outline,
        Dots
    }
}
