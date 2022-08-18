package com.khanhpham.tothemoon.core.energy;

public class GeneratorEnergyStorage extends Energy{
    public GeneratorEnergyStorage(int capacity, int generateRate) {
        super(capacity, generateRate, capacity);
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
