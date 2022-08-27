package com.khanhpham.tothemoon.utils.capabilities;

import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

/**
 *
 */
public class ItemStackEnergy extends EnergyStorage {
    public final CompoundTag ttmDataTag;

    public ItemStackEnergy(ItemStack stack, int capacity) {
        super(capacity);
        this.ttmDataTag = getNbtDataTag(stack);
        energy = ttmDataTag.getInt("energy");
    }

    public static int getEnergy(ItemStack stack) {
        return getNbtDataTag(stack).getInt(LootUtils.LOOT_DATA_ENERGY);
    }

    private static CompoundTag getNbtDataTag(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("ttmData", LootUtils.TAG_TYPE_COMPOUND)) {
            CompoundTag ttmData = new CompoundTag();
            ttmData.putInt(LootUtils.LOOT_DATA_ENERGY, 0);
            tag.put("ttmData", ttmData);
            return ttmData;
        }
        return tag;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int i = super.receiveEnergy(maxReceive, simulate);
        updateEnergyNbt();
        return i;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int i = super.extractEnergy(maxExtract, simulate);
        updateEnergyNbt();
        return i;
    }

    private void updateEnergyNbt() {
        if (ttmDataTag.contains(LootUtils.LOOT_DATA_ENERGY, LootUtils.TAG_TYPE_INT)) {
            ttmDataTag.putInt(LootUtils.LOOT_DATA_ENERGY, this.energy);
        }
    }

}
