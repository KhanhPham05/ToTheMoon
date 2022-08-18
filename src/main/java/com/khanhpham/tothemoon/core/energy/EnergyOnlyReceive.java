package com.khanhpham.tothemoon.core.energy;

/**
 * This class indicate an EnergyStorage but can not be extracted !
 */
public class EnergyOnlyReceive extends Energy {
    public EnergyOnlyReceive(int capacity) {
        this(capacity, 300);
    }

    public EnergyOnlyReceive(int capacity, int workingEnergyCost) {
        super(capacity, capacity, workingEnergyCost);
    }

    @Override
    public final boolean canExtract() {
        return false;
    }
}
