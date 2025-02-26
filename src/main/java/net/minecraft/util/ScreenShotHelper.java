package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ScreenShotHelper
{
    private static final Logger logger = LogManager.getLogger();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    /** A buffer to hold pixel values returned by OpenGL. */
    private static IntBuffer pixelBuffer;

    /**
     * The built-up array that contains all the pixel values returned by OpenGL.
     */
    private static int[] pixelValues;
    private static final String __OBFID = "CL_00000656";

    /**
     * Saves a screenshot in the game directory with a time-stamped filename.  Args: gameDirectory,
     * requestedWidthInPixels, requestedHeightInPixels, frameBuffer
     */
    public static IChatComponent saveScreenshot(File mcDataDir, int width, int height, Framebuffer framebufferIn)
    {
        return saveScreenshot(mcDataDir, (String)null, width, height, framebufferIn);
    }

    /**
     * Saves a screenshot in the game directory with the given file name (or null to generate a time-stamped name).
     * Args: gameDirectory, fileName, requestedWidthInPixels, requestedHeightInPixels, frameBuffer
     */
    public static IChatComponent saveScreenshot(File mcDataDir, String fileName, int width, int height, Framebuffer framebufferIn)
    {
        try
        {
            File var5 = new File(mcDataDir, "screenshots");
            var5.mkdir();

            if (OpenGlHelper.isFramebufferEnabled())
            {
                width = framebufferIn.framebufferTextureWidth;
                height = framebufferIn.framebufferTextureHeight;
            }

            int var6 = width * height;

            if (pixelBuffer == null || pixelBuffer.capacity() < var6)
            {
                pixelBuffer = BufferUtils.createIntBuffer(var6);
                pixelValues = new int[var6];
            }

            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            pixelBuffer.clear();

            if (OpenGlHelper.isFramebufferEnabled())
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferIn.framebufferTexture);
                GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
            }
            else
            {
                GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
            }

            pixelBuffer.get(pixelValues);
            TextureUtil.arrayFlipVertical(pixelValues, width, height);
            BufferedImage bufferedImg;

            if (OpenGlHelper.isFramebufferEnabled())
            {
                bufferedImg = new BufferedImage(framebufferIn.framebufferWidth, framebufferIn.framebufferHeight, 1);
                int var8 = framebufferIn.framebufferTextureHeight - framebufferIn.framebufferHeight;

                for (int h = var8; h < framebufferIn.framebufferTextureHeight; ++h)
                {
                    for (int w = 0; w < framebufferIn.framebufferWidth; ++w)
                    {
                        bufferedImg.setRGB(w, h - var8, pixelValues[h * framebufferIn.framebufferTextureWidth + w]);
                    }
                }
            }
            else
            {
                bufferedImg = new BufferedImage(width, height, 1);
                bufferedImg.setRGB(0, 0, width, height, pixelValues, 0, width);
            }

            File var12;

            if (fileName == null)
            {
                var12 = getTimestampedPNGFileForDirectory(var5);
            }
            else
            {
                var12 = new File(var5, fileName);
            }

            ImageIO.write(bufferedImg, "png", var12);
            ChatComponentText var13 = new ChatComponentText(var12.getName());
            var13.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, var12.getAbsolutePath()));
            var13.getChatStyle().setUnderlined(true);
            return new ChatComponentTranslation("screenshot.success", var13);
        }
        catch (Exception var11)
        {
            logger.warn("Couldn't save screenshot", var11);
            return new ChatComponentTranslation("screenshot.failure", var11.getMessage());
        }
    }

    /**
     * Creates a unique PNG file in the given directory named by a timestamp.  Handles cases where the timestamp alone
     * is not enough to create a uniquely named file, though it still might suffer from an unlikely race condition where
     * the filename was unique when this method was called, but another process or thread created a file at the same
     * path immediately after this method returned.
     */
    public static File getTimestampedPNGFileForDirectory(File par0File)
    {
        String var2 = dateFormat.format(new Date());
        int var3 = 1;

        while (true)
        {
            File var1 = new File(par0File, var2 + (var3 == 1 ? "" : "_" + var3) + ".png");

            if (!var1.exists())
            {
                return var1;
            }

            ++var3;
        }
    }
}
