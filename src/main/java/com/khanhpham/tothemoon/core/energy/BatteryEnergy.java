package com.khanhpham.tothemoon.core.energy;

public class BatteryEnergy extends Energy {

    public BatteryEnergy(int capacity) {
        super(capacity);
        this.batteryType = BatteryEnergyType.NORMAL;
    }

    private final BatteryEnergyType batteryType;

    public BatteryEnergyType getBatteryType() {
        return batteryType;
    }

    @Override
    public boolean canReceive() {
        return getBatteryType() == BatteryEnergyType.NORMAL && super.canReceive();
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if (getBatteryType() == BatteryEnergyType.ALWAYS_FULL) {
            fillToFull();
        }
        return extractedEnergy;
    }

    public void fillToFull() {
        this.energy = this.capacity;
    }

    public enum BatteryEnergyType {
        ALWAYS_FULL,
        NORMAL
    }
}
