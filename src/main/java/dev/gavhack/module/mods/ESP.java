package dev.gavhack.module.mods;

import dev.gavhack.events.RenderModelEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.BooleanSetting;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.FloatSetting;
import dev.gavhack.util.OutlineUtil;
import dev.gavhack.util.shader.FramebufferShader;
import dev.gavhack.util.shader.shaders.GalaxyShader;
import dev.gavhack.util.shader.shaders.OutlineShader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ESP extends Module {

    private final EnumSetting<EspMode> espMode = new EnumSetting<>("Mode", EspMode.Shader);

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
        super("ESP", "shader esp", Category.RENDER);
    }

    private final FramebufferShader shader = new OutlineShader();

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event) {
        if (espMode.getValue() != EspMode.Shader)
            return;

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
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
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onModelRender(RenderModelEvent event) {
        event.setCanceled(true);

        if (shouldEsp(event.getEntity())) {
            boolean prevFancy = mc.gameSettings.fancyGraphics;
            mc.gameSettings.fancyGraphics = false;
            final Color clr = new Color(red.getValue(), green.getValue(), blue.getValue());
            GlStateManager.color(1f, 1f, 1f, 1f);
            OutlineUtil.setColor(clr);
            OutlineUtil.renderOne(lineWidth.getValue());
            event.render();
            OutlineUtil.setColor(clr);
            OutlineUtil.renderTwo();
            event.render();
            OutlineUtil.setColor(clr);
            OutlineUtil.renderThree();
            event.render();
            OutlineUtil.setColor(clr);
            OutlineUtil.renderFour(clr);
            event.render();
            OutlineUtil.setColor(clr);
            OutlineUtil.renderFive();
            OutlineUtil.setColor(Color.WHITE);
            mc.gameSettings.fancyGraphics = prevFancy;
        }
    }

    private boolean shouldEsp(Entity entity) {
        if (entity instanceof EntityLivingBase) {
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
}
