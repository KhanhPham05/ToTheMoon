package com.khanhpham.tothemoon.core.energy;

/**
 * This class indicate an EnergyStorage but can not be extracted !
 */
public class EnergyOnlyReceive extends Energy {
    public EnergyOnlyReceive(int capacity) {
        super(capacity);
    }

    @Override
    public final boolean canExtract() {
        return false;
    }
}
