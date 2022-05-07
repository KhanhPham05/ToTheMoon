package com.khanhpham.tothemoon.utils.energy;

public class BatteryEnergy extends Energy {
    public BatteryEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return super.receiveEnergy(maxReceive, simulate);
    }
}
