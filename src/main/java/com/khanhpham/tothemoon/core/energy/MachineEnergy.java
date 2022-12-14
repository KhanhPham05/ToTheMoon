package com.khanhpham.tothemoon.core.energy;

/**
 * This class indicate an EnergyStorage but can not be extracted !
 */
public class MachineEnergy extends Energy {
    public MachineEnergy(int capacity) {
        this(capacity, 300);
    }

    public MachineEnergy(int capacity, int workingEnergyCost) {
        super(capacity, capacity, workingEnergyCost);
    }

    @Override
    public final boolean canExtract() {
        return false;
    }
}
