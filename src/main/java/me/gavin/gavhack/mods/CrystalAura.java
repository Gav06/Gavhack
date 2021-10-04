package me.gavin.gavhack.mods;

import me.gavin.gavhack.events.PacketEvent;
import me.gavin.gavhack.events.PlayerLivingUpdateEvent;
import me.gavin.gavhack.misc.BlockUtil;
import me.gavin.gavhack.misc.DamageUtil;
import me.gavin.gavhack.mods.api.Module;
import me.gavin.gavhack.settings.BooleanSetting;
import me.gavin.gavhack.settings.FloatSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrystalAura extends Module {

    private final BooleanSetting breaking = new BooleanSetting("Break", true);
    private final BooleanSetting placing = new BooleanSetting("Place", true);
    private final FloatSetting breaksPerTick = new FloatSetting("BreaksPerTick", 1f, 3f, 1f);
    private final FloatSetting breakSpeed = new FloatSetting("BreakSpeed", 1f, 0f, 20f);
    private final FloatSetting placeSpeed = new FloatSetting("PlaceSpeed", 1f, 0f, 20f);
    private final FloatSetting range = new FloatSetting("Range", 4f, 1f, 6f);
    private final FloatSetting wallsRange = new FloatSetting("WallsRange", 3f, 0f, 5f);

    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting animals = new BooleanSetting("Animals", true);
    private final BooleanSetting mobs = new BooleanSetting("Mobs", true);
    private final BooleanSetting others = new BooleanSetting("Players", true);

    public CrystalAura() {
        super("CrystalAura", "Place/Break crystals automatically", Category.Combat);
        this.settings.add(breaking);
        this.settings.add(placing);
        this.settings.add(breaksPerTick);
        this.settings.add(breakSpeed);
        this.settings.add(placeSpeed);
        this.settings.add(range);
        this.settings.add(wallsRange);

        this.settings.add(players);
        this.settings.add(animals);
        this.settings.add(mobs);
        this.settings.add(others);
    }

    @SubscribeEvent
    public void onTick(PlayerLivingUpdateEvent event) {
        final EntityEnderCrystal targetCrystal = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .map(entity -> (EntityEnderCrystal) entity)
                .sorted(Comparator.comparing(crystal -> crystal.getDistance(mc.player)))
                .findFirst()
                .orElse(null);

        if (targetCrystal != null && breaking.isEnabled()) {
            float range = mc.player.canEntityBeSeen(targetCrystal) ? this.range.getValue() : this.wallsRange.getValue();

            if (mc.player.getDistance(targetCrystal) <= range) {
                mc.playerController.attackEntity(mc.player, targetCrystal);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }

        if (placing.isEnabled()) {
            EntityLivingBase entity = mc.world.loadedEntityList.stream()
                    .filter(e -> e instanceof EntityLivingBase)
                    .filter(e -> !e.equals(mc.player))
                    .map(e -> (EntityLivingBase) e)
                    .sorted(Comparator.comparing(e -> e.getDistance(mc.player)))
                    .filter(this::shouldAttack)
                    .findFirst()
                    .orElse(null);

            if (entity != null) {
                int crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
                if (crystalSlot == -1) {
                    for (int l = 0; l < 9; ++l) {
                        if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                            crystalSlot = l;
                            break;
                        }
                    }
                }

                if (crystalSlot != mc.player.inventory.currentItem && crystalSlot != -1) {
                    mc.player.inventory.currentItem = crystalSlot;
                    mc.player.connection.sendPacket(new CPacketHeldItemChange());
                }

                List<BlockPos> blocks = getPossibleBlocks();

                BlockPos bestPos = null;
                double bestDamage = -1;
                for (BlockPos pos : blocks) {
                    double damage = DamageUtil.calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity);
                    if (damage > bestDamage) {
                        bestDamage = damage;
                        bestPos = pos;
                    }
                }

                if (bestPos != null) {
                    mc.playerController.processRightClickBlock(mc.player, mc.world, bestPos, EnumFacing.UP, Vec3d.ZERO, EnumHand.MAIN_HAND);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {

    }

    private List<BlockPos> getPossibleBlocks() {
        return BlockUtil.getSurroundingPositions(mc.player, (int) range.getValue(), true).stream()
                .filter(this::canPlace)
                .collect(Collectors.toList());
    }

    private boolean canPlace(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
                || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
                && mc.world.getBlockState(boost).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    private boolean shouldAttack(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return this.players.isEnabled();
        } else if (entity instanceof EntityAnimal) {
            return this.animals.isEnabled();
        } else if (entity instanceof EntityMob) {
            return this.mobs.isEnabled();
        } else {
            return this.others.isEnabled();
        }
    }
}