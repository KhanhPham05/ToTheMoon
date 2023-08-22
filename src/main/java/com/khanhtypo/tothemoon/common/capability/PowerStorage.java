package com.khanhtypo.tothemoon.common.capability;

import net.minecraftforge.energy.EnergyStorage;

public class PowerStorage extends EnergyStorage {
    public PowerStorage(int capacity) {
        super(capacity);
    }

    @Override
    public boolean canExtract() {
        return super.canExtract() && !this.isEmpty();
    }

    @Override
    public boolean canReceive() {
        return super.canReceive() && !this.isFull();
    }

    public boolean isEmpty() {
        return super.getEnergyStored() <= 0;
    }

    public int getPowerSpace() {
        return Math.max(0, super.getMaxEnergyStored() - super.getEnergyStored());
    }

    public boolean isFull() {
        return super.getEnergyStored() >= super.getMaxEnergyStored();
    }
}
