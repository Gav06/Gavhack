package net.minecraft.client.gui.inventory;

import me.gavin.gavhack.module.impl.SignColors;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEditSign extends GuiScreen
{
    protected String field_146850_a = "Edit sign message:";
    private TileEntitySign tileEntitySign;
    private int field_146849_g;
    private int currentLine;
    private GuiButton completeButton;
    private static final String __OBFID = "CL_00000764";

    public GuiEditSign(TileEntitySign par1TileEntitySign)
    {
        this.tileEntitySign = par1TileEntitySign;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.completeButton = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
        this.tileEntitySign.func_145913_a(false);
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        NetHandlerPlayClient var1 = this.mc.getNetHandler();

        if (var1 != null)
        {
            if (SignColors.INSTANCE.isEnabled()) {
                for (int i = 0; i < tileEntitySign.text.length; i++) {
                    this.tileEntitySign.text[i] = this.tileEntitySign.text[i].replaceAll("&", "\u00a7");
                }
            }

            var1.addToSendQueue(new C12PacketUpdateSign(this.tileEntitySign.x, this.tileEntitySign.y, this.tileEntitySign.z, this.tileEntitySign.text));
        }

        this.tileEntitySign.func_145913_a(true);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.field_146849_g;
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.enabled)
        {
            if (p_146284_1_.id == 0)
            {
                this.tileEntitySign.onInventoryChanged();
                this.mc.displayGuiScreen(null);
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char keyChar, int par2)
    {
        if (par2 == Keyboard.KEY_UP)
        {
            this.currentLine = this.currentLine - 1 & 3;
        }

        if (par2 == Keyboard.KEY_DOWN || par2 == Keyboard.KEY_RETURN || par2 == Keyboard.KEY_NUMPADENTER)
        {
            this.currentLine = this.currentLine + 1 & 3;
        }

        if (par2 == Keyboard.KEY_BACK && !this.tileEntitySign.text[this.currentLine].isEmpty())
        {
            this.tileEntitySign.text[this.currentLine] = this.tileEntitySign.text[this.currentLine].substring(0, this.tileEntitySign.text[this.currentLine].length() - 1);
        }

        if (ChatAllowedCharacters.isAllowedCharacter(keyChar) && this.tileEntitySign.text[this.currentLine].length() < 15)
        {
            this.tileEntitySign.text[this.currentLine] = this.tileEntitySign.text[this.currentLine] + keyChar;
        }

        if (par2 == Keyboard.KEY_ESCAPE)
        {
            this.actionPerformed(this.completeButton);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146850_a, this.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2), 0.0F, 50.0F);
        float var4 = 93.75F;
        GL11.glScalef(-var4, -var4, -var4);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        Block var5 = this.tileEntitySign.getBlockType();

        if (var5 == Blocks.standing_sign)
        {
            float var6 = (float)(this.tileEntitySign.getBlockMetadata() * 360) / 16.0F;
            GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        }
        else
        {
            int var8 = this.tileEntitySign.getBlockMetadata();
            float var7 = 0.0F;

            if (var8 == 2)
            {
                var7 = 180.0F;
            }

            if (var8 == 4)
            {
                var7 = 90.0F;
            }

            if (var8 == 5)
            {
                var7 = -90.0F;
            }

            GL11.glRotatef(var7, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        }

        if (this.field_146849_g / 6 % 2 == 0)
        {
            this.tileEntitySign.field_145918_i = this.currentLine;
        }

        TileEntityRendererDispatcher.instance.func_147549_a(this.tileEntitySign, -0.5D, -0.75D, -0.5D, 0.0F);
        this.tileEntitySign.field_145918_i = -1;
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
