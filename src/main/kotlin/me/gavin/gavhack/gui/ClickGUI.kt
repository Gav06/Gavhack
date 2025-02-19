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
import me.gavin.gavhack.utilities.shaders.BackgroundShader
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.util.concurrent.CopyOnWriteArrayList

object ClickGUI: GuiScreen() {

    var previousGui: GuiScreen? = null
    val windows = CopyOnWriteArrayList<Window>()

    private var shouldOpenGui = false

    /* Hacky way to get gui to open after typing the command in the chat box */
    private val livingTick = Listener<ClientTickEvent> {
        val minecraft = Minecraft.getMinecraft()
        if (minecraft != null && shouldOpenGui && minecraft.currentScreen == null) {
            shouldOpenGui = false
            minecraft.displayGuiScreen(ClickGUI)
        }
    }

    val backgroundShader = BackgroundShader()

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (mc.theWorld == null)
            drawDefaultBackground()
        else if (GuiModule.drawBackground.value) {
            val rgba = 0x30ffffff
            drawRect(0, 0, width, height, rgba)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)

        val res = ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight)

        GL11.glPushMatrix()

        backgroundShader.startShader()
        mc.framebuffer.bindFramebufferTexture()
        backgroundShader.updateUniforms()

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, res.scaledHeight_double);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(res.scaledWidth_double, res.scaledHeight_double);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d(res.scaledWidth_double, 0.0);
        GL11.glEnd();

        backgroundShader.stopShader()
        GL11.glPopMatrix()
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)

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