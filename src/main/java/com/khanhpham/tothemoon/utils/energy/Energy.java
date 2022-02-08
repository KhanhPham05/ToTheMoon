package com.khanhpham.tothemoon.utils.energy;

import net.minecraftforge.energy.EnergyStorage;

public class Energy extends EnergyStorage {
    private final int maxReceive;
    private final int maxExtract;

    public Energy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public void setChanged() {

    }
}
