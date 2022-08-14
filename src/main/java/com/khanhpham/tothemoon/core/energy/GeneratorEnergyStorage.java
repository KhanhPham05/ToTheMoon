package com.khanhpham.tothemoon.core.energy;

public class GeneratorEnergyStorage extends Energy{
    @Deprecated
    public GeneratorEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public GeneratorEnergyStorage(int capacity, int generateRate) {
        super(capacity);
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
