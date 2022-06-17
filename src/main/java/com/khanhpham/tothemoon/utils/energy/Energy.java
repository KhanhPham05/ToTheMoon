package com.khanhpham.tothemoon.utils.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class Energy extends EnergyStorage {
    protected final int maxReceive;
    protected final int maxExtract;

    public Energy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
    }

    public Energy(int capacity) {
        super(capacity);
        this.maxReceive = capacity;
        this.maxExtract = capacity;
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

    public void receiveEnergyIgnoreCondition() {
        if (this.energy + this.maxReceive >= this.capacity) {
            this.energy += (this.capacity - energy);
        } else {
            this.energy += this.maxReceive;
        }
    }

    public void setEnergy(int energyNbt) {
        this.energy = energyNbt;
    }
}
