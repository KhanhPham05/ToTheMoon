package com.khanhpham.tothemoon.utils.energy;

/**
 * This class indicate an EnergyStorage but can not be extracted !
 */
public class EnergyReceivable extends Energy {
    public EnergyReceivable(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public final boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return this.energy < this.capacity;
    }
}
