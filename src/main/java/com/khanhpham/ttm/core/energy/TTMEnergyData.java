package com.khanhpham.ttm.core.energy;

import com.khanhpham.ttm.ToTheMoonMain;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class TTMEnergyData extends EnergyStorage {

    public TTMEnergyData(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }


    public void setChanged() {
    }

    public void addEnergy() {
        int spaceEnergy = capacity - energy;
        if (spaceEnergy < maxReceive) {
            energy += spaceEnergy;
            ToTheMoonMain.LOG.info("Adding " + spaceEnergy + " FE");
        } else {
            energy += maxReceive;
            ToTheMoonMain.LOG.info("Adding " + maxReceive + " FE");
        }
    }

    public void removeEnergy() {
        if (maxExtract > energy) {
            energy = 0;
        } else {
            energy =- maxExtract;
        }
    }

    public CompoundTag save(CompoundTag tag) {
        tag.putInt("energy", energy);
        return tag;
    }

    public void load(CompoundTag tag) {
        energy = tag.getInt("energy");
    }
}
