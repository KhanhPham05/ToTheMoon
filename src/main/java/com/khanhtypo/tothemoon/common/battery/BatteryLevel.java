package com.khanhtypo.tothemoon.common.battery;

public enum BatteryLevel {
    STANDARD(750_000),
    REDSTONE(1_500_000),
    STEEL(3_000_000);

    public final int energyCapacity;

    BatteryLevel(int energyCapacity) {
        this.energyCapacity = energyCapacity;
    }
}
