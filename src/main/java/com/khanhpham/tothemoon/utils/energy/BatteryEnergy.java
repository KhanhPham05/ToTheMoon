package com.khanhpham.tothemoon.utils.energy;

public class BatteryEnergy extends Energy {
    public BatteryEnergy(int capacity, int maxReceive, int maxExtract, BatteryEnergyType batteryType) {
        super(capacity, maxReceive, maxExtract);
        this.batteryType = batteryType;
    }

    public BatteryEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
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
