package com.khanhtypo.tothemoon.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

public class NbtPowerStorage extends PowerStorage {
    private CompoundTag energyTag;

    public NbtPowerStorage(int capacity) {
        super(capacity);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int acceptedEnergy = super.receiveEnergy(maxReceive, simulate);
        if (!simulate && acceptedEnergy > 0) {
            this.updateTag();
        }
        return acceptedEnergy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if (!simulate && extractedEnergy > 0) {
            this.updateTag();
        }
        return extractedEnergy;
    }

    private void updateTag() {
        int energy = super.getEnergyStored();
        if (energy > 0) {
            this.energyTag.putInt("Amount", energy);
        } else {
            this.energyTag.remove("Amount");
        }
    }

    @Override
    public void deserializeNBT(@Nullable Tag energyTag) {
        super.deserializeNBT(energyTag);
        if (energyTag instanceof CompoundTag compoundTag) {
            this.energyTag = compoundTag;
        }
    }
}
