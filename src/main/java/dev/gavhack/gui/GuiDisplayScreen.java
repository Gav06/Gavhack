package dev.gavhack.gui;

import dev.gavhack.gui.api.AbstractComponent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.module.ModuleManager;
import dev.gavhack.module.mods.GuiModule;
import dev.gavhack.util.MathUtil;
import dev.gavhack.util.shader.shaders.BlurShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiDisplayScreen extends GuiScreen {


    private long openedTime = -1L;
    private final List<AbstractComponent> components;

    private final BlurShader blurShader = new BlurShader();

    public GuiDisplayScreen(ModuleManager modManager) {
        this.components = new ArrayList<>();
        int x = 2;
        for (Category category : Category.values()) {
            if (category != Category.HUD) {
                final Panel panel = new Panel(category, x, 2, 110, 12);
                x += 112;
                int y = 0;
                for (Module module : modManager.getCategoryModules(category)) {
                    panel.getButtons().add(new Button<>(module, panel.x, panel.y + panel.height + y, panel.width, panel.height));
                    y += panel.height;
                }

                components.add(panel);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        components.forEach(c -> c.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        components.forEach(c -> c.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void initGui() {
        openedTime = System.currentTimeMillis();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
//        blurShader.startDraw(partialTicks);
//        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 0x30ffffff);
//        blurShader.stopDraw();

        if (GuiModule.background.isEnabled())
            drawDefaultBackground();

        GlStateManager.pushMatrix();
        if (GuiModule.openAnimation.getValue() == GuiModule.OpenAnimation.MoneyMod) {
            final float scale = MathHelper.clamp(MathUtil.getSmooth2Animation(500, System.currentTimeMillis() - openedTime), 0f, 1.0f);
            GlStateManager.scale(scale, scale, 0f);
        }
        components.forEach(c -> c.draw(mouseX, mouseY, partialTicks));
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        if (GuiModule.descriptions.isEnabled()) {
            for (AbstractComponent component : components) {
                if (component instanceof Panel) {
                    final Panel panel = (Panel) component;
                    for (Button<?> button : panel.getButtons()) {
                        if (button.inside(mouseX, mouseY)) {
                            Gui.drawRect(
                                    mouseX + 5,
                                    mouseY - 7,
                                    mc.fontRenderer.getStringWidth(button.getModule().getDescription()) + mouseX + 7,
                                    mouseY + 3,
                                    0x80000000);
                            mc.fontRenderer.drawStringWithShadow(button.getModule().getDescription(), mouseX + 6, mouseY - 6, -1);
                            GlStateManager.popMatrix();
                            return;
                        }
                    }
                }
            }
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) throws IOException {
        super.keyTyped(keyChar, keyCode);
        components.forEach(c -> c.keyTyped(keyCode));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public List<AbstractComponent> getComponents() {
        return components;
    }

    public static void clickSound() {
        if (GuiModule.sound.isEnabled()) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
    }
}