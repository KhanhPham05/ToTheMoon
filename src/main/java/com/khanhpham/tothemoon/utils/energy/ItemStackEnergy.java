package com.khanhpham.tothemoon.utils.energy;

import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

public class ItemStackEnergy extends EnergyStorage {
    private final CompoundTag blockEntityTag;

    public ItemStackEnergy(ItemStack stack, int capacity) {
        super(capacity);
        this.blockEntityTag = getNbt(stack);
        energy = blockEntityTag.getInt("energy");
    }

    public static CompoundTag getNbt(ItemStack stack) {
        return LootUtils.getBlockEntityTag(stack);
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
        if (blockEntityTag.contains(LootUtils.LOOT_DATA_ENERGY, LootUtils.TAG_TYPE_INT)) {
            blockEntityTag.remove(LootUtils.LOOT_DATA_ENERGY);
            blockEntityTag.putInt(LootUtils.LOOT_DATA_ENERGY, this.energy);
        }
    }

}
