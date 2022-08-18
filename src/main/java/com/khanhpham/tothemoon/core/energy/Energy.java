package com.khanhpham.tothemoon.core.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class Energy extends EnergyStorage {
    public Energy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public Energy(int capacity) {
        super(capacity);
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public boolean isFull() {
        return super.energy >= super.capacity;
    }

    public boolean isEmpty() {
        return this.getAvailableAmount() >= this.capacity;
    }

    public void consumeEnergyIgnoreCondition() {
        this.energy -= Math.min(this.energy, this.maxExtract);
    }

    public void save(CompoundTag pTag) {
        pTag.putInt("energy", energy);
    }

    public void load(CompoundTag pTag) {
        this.energy = pTag.getInt("energy");
    }

    public int getAvailableAmount() {
        return this.capacity - this.energy;
    }

    public void generateEnergy() {
        int energyReceived = Math.min(capacity - energy, this.maxReceive);
        energy += energyReceived;
    }

    public void setEnergy(int energyNbt) {
        this.energy = energyNbt;
    }
}
