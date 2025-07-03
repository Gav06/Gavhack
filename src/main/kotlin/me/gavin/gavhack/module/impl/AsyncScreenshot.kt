package me.gavin.gavhack.module.impl

import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.event.events.ClientTickEvent
import me.gavin.gavhack.event.events.ScreenshotEvent
import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.utilities.Utils
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.event.ClickEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.util.ScreenShotHelper
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.concurrent.thread


object AsyncScreenshot: Module("AsyncScreenshot", "Make screenshots multi-threaded", ModCategory.MISC) {

    private var inProgress = false
    private lateinit var screenshotThread: Thread

    // adapted from minecraft's screenshot function
    // we still need to copy the pixels in the main thread because that's where the OpenGL context lives,
    // but we actually create the image file in the other thread because that's what lags your game the most
    private fun grabScreenPixels(event: ScreenshotEvent): IntArray {
        var width = event.width
        var height = event.height

        if (OpenGlHelper.isFramebufferEnabled()) {
            width = event.framebuffer.framebufferTextureWidth
            height = event.framebuffer.framebufferTextureHeight
        }

        val size = width * height
        val pixelBuffer = BufferUtils.createIntBuffer(size)
        val pixelValues = IntArray(size)

        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, GL11.GL_ONE)
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, GL11.GL_ONE)
        pixelBuffer.clear()

        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, event.framebuffer.framebufferTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        }
        else {
            GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        }

        pixelBuffer.get(pixelValues)
        TextureUtil.imgDataFlipVertical(pixelValues, width, height)
        return pixelValues
    }

    private fun saveAsyncScreenshot(pixelValues: IntArray, e: ScreenshotEvent) {
        val bufferedImg: BufferedImage

        if (OpenGlHelper.isFramebufferEnabled()) {
            bufferedImg = BufferedImage(e.framebuffer.framebufferWidth, e.framebuffer.framebufferHeight, 1)
            val var8: Int = e.framebuffer.framebufferTextureHeight - e.framebuffer.framebufferHeight

            for (h in var8 until e.framebuffer.framebufferTextureHeight) {
                for (w in 0 until e.framebuffer.framebufferWidth) {
                    bufferedImg.setRGB(
                        w, h - var8,
                        pixelValues[h * e.framebuffer.framebufferTextureWidth + w]
                    )
                }
            }
        } else {
            bufferedImg = BufferedImage(mc.displayWidth, mc.displayHeight, 1)
            bufferedImg.setRGB(0, 0, mc.displayWidth, mc.displayHeight, pixelValues, 0, mc.displayWidth)
        }

        val ssFolder = File(e.dataDir, "screenshots")
        ssFolder.mkdir()
        val pngFile = ScreenShotHelper.getTimestampedPNGFileForDirectory(ssFolder)

        ImageIO.write(bufferedImg, "png", pngFile)

        val chatComponent = ChatComponentText(pngFile.name)
        chatComponent.chatStyle.setChatClickEvent(ClickEvent(ClickEvent.Action.OPEN_FILE, pngFile.canonicalPath))
        chatComponent.chatStyle.setUnderlined(true)
        mc.ingameGUI.chatGui.printChatMessage(ChatComponentTranslation("screenshot.success", chatComponent))
    }

    private val screenshotListener = Listener<ScreenshotEvent> { (it as ScreenshotEvent)
        it.cancelled = true
        if (!inProgress) {
            Utils.gameMessage("Taking screenshot!")
            inProgress = true
            val pixels = grabScreenPixels(it)
            screenshotThread = thread(
                start = true, isDaemon = false, contextClassLoader = null, name = "ScreenshotThread", priority = -1
            ) { saveAsyncScreenshot(pixels, it) }
        }
    }

    private val tickListener = Listener<ClientTickEvent> {
        if (inProgress && !screenshotThread.isAlive) {
            inProgress = false
        }
    }

    init {
        addEventListener(screenshotListener, ScreenshotEvent::class)
        addEventListener(tickListener, ClientTickEvent::class)
    }

}