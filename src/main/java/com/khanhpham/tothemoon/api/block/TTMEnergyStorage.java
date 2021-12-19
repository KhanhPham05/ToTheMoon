package com.khanhpham.tothemoon.api.block;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public abstract class TTMEnergyStorage extends EnergyStorage {
    public TTMEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        if (capacity == 0 || maxTransfer == 0) throw new IllegalStateException("energy storage data can not be 0");
    }

    public void addEnergy(int amount) {
        this.energy = Math.min(this.energy + amount, getMaxEnergyStored());
    }

    public void removeEnergy(int amount) {
        this.energy = Math.max(this.energy - amount, 0);
    }

    public abstract void setChanged();

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public void save(CompoundNBT nbt) {
        nbt.putInt("energy", energy);
        nbt.putInt("maxEnergy", capacity);
    }

    public void load(CompoundNBT nbt) {
        energy = nbt.getInt("energy");
        capacity = nbt.getInt("maxEnergy");
    }
}
