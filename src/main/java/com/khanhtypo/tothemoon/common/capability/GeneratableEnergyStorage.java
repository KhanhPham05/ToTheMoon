package com.khanhtypo.tothemoon.common.capability;

import net.minecraftforge.energy.EnergyStorage;

public class GeneratableEnergyStorage extends EnergyStorage {
    public GeneratableEnergyStorage(int capacity) {
        super(capacity);
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    public void generatePower(int generationAmount) {
        super.energy += Math.min(generationAmount, this.getEnergySpace());
    }

    public int getEnergySpace() {
        return super.getMaxEnergyStored() - this.getEnergyStored();
    }
}
