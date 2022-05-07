package com.khanhpham.tothemoon.utils.energy;

public class ExtractableEnergy extends Energy{
    public ExtractableEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
