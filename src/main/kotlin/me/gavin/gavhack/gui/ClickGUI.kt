package me.gavin.gavhack.gui

import me.gavin.gavhack.config.ConfigManager
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ClientTickEvent
import me.gavin.gavhack.event.events.PlayerLivingTickEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.ModuleManager
import me.gavin.gavhack.module.impl.GuiModule
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.FloatSetting
import me.gavin.gavhack.module.setting.IntSetting
import me.gavin.gavhack.module.setting.ToggleSetting
import me.gavin.gavhack.utilities.FramebufferEffect
import me.gavin.gavhack.utilities.shaders.BlurShader
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.Tessellator
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11
import java.util.concurrent.CopyOnWriteArrayList

object ClickGUI: GuiScreen() {

    private val windows = CopyOnWriteArrayList<Window>()
    private var shouldOpenGui = false



    /* Hacky way to get gui to open after typing the command in the chat box */
    private val livingTick = Listener<ClientTickEvent> {
        val minecraft = Minecraft.getMinecraft()
        if (minecraft != null && shouldOpenGui && minecraft.currentScreen == null) {
            shouldOpenGui = false
            minecraft.displayGuiScreen(ClickGUI)
        }
    }


    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (mc.theWorld == null)
            drawDefaultBackground()
        else if (GuiModule.drawBackground.value) {
            drawBackgroundShader()
        }
        super.drawScreen(mouseX, mouseY, partialTicks)



        if (mc.theWorld == null) {
            fontRendererObj.drawStringWithShadow("Press ESCAPE to go back", 2, height - 11, -1)
        }

        for (comp in windows.reversed())
            comp.onRender(mouseX, mouseY, partialTicks)


        // Draw le epic descriptions
        if (!GuiModule.drawDesc.value)
            return

        for (comp in windows) {
            if (comp.isInside(mouseX, mouseY))
                break

            for (button in comp.components) {
                if (!button.isInside(mouseX, mouseY))
                    continue

                val desc = button.module.desc
                this.drawToolTipBox(listOf(desc), mouseX + 5, mouseY + 5)
                return
            }
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)

        for (comp in windows) {
            val fullRect = Rect(comp.x, comp.y, comp.w, comp.getRenderHeight())
            if (fullRect.isInside(mouseX, mouseY)) {
                comp.onClick(mouseX, mouseY, button)
                // move it to front
                // only possible because of CopyOnWriteArrayList
                windows.remove(comp)
                windows.add(0, comp)
                break
            }
        }
    }

    override fun mouseMovedOrUp(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseMovedOrUp(mouseX, mouseY, button)

        for (comp in windows)
            comp.onRelease(mouseX, mouseY, button)
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        super.keyTyped(keyChar, keyCode)

        for (comp in windows)
            comp.keyTyped(keyChar, keyCode)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    fun buttonClick() {
        mc.soundHandler.playSound(PositionedSoundRecord.getMasterRecord(ResourceLocation("gui.button.press"), 1.0f))
    }

    private val vertBlurFbo = FramebufferEffect(
        BlurShader(BlurShader.Type.VERTICAL),
        Display.getWidth(), Display.getHeight(), false
    )
    private val horizBlurShader = BlurShader(BlurShader.Type.HORIZONTAL)

    /**
     * We use a FBO for the vertical pass, just so we don't draw 2 quads to the mc frame buffer,
     * when we only need one
     */
    private fun drawBackgroundShader() {
        val res = ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight)

        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glEnable(GL11.GL_TEXTURE_2D)

        mc.framebuffer.unbindFramebuffer()
        vertBlurFbo.updateFramebuffer(mc.displayWidth, mc.displayHeight)
        vertBlurFbo.bindBuffer()
        vertBlurFbo.shader.startShader()
        vertBlurFbo.shader.updateUniforms()
        FramebufferEffect.drawFullscreenTexQuad(mc.framebuffer.framebufferTexture, res)
        vertBlurFbo.shader.stopShader()
        vertBlurFbo.unbindBuffer()
        mc.framebuffer.bindFramebuffer(true)

        // vertical blur pass
        horizBlurShader.startShader()
        horizBlurShader.updateUniforms()
        FramebufferEffect.drawFullscreenTexQuad(vertBlurFbo.getFrameBuffer().framebufferTexture, res)
        horizBlurShader.stopShader()
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glPopMatrix()
    }


    init {
        EventBus.addListener(livingTick, PlayerLivingTickEvent::class)

        var xOffset = 0
        for (category in ModCategory.entries) {
            // We can pass 0 as height since it will automatically be resized anyway.
            val window = Window(4 + xOffset, 4, 100, 0, category.title, category)

            for (module in ModuleManager.getCategoryMods(category)) {
                val button = WindowButton(module, 0, 0, window.w, window.handle.h - 2)

                for (setting in module.settings) {
                    // add setting components
                    when (setting) {
                        is ToggleSetting -> button.subComponents.add(BooleanComponent(setting, button.w, button.h))
                        is FloatSetting -> button.subComponents.add(FloatComponent(setting, button.w, button.h))
                        is EnumSetting<*> -> button.subComponents.add(EnumComponent(setting, button.w, button.h))
                        is IntSetting -> button.subComponents.add(IntComponent(setting, button.w, button.h))
                    }
                }

                button.subComponents.add(BindComponent(module, button.w, button.h))
                window.components.add(button)
            }

            ConfigManager.addConfig(window)
            windows.add(window)

            xOffset += window.w + 4
        }
    }

}