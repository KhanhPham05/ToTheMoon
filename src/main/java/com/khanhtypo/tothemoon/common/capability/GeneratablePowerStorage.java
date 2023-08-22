package com.khanhtypo.tothemoon.common.capability;

public class GeneratablePowerStorage extends PowerStorage {
    public GeneratablePowerStorage(int capacity) {
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
