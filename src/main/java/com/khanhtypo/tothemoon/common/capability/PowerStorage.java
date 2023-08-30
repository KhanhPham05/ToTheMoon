package com.khanhtypo.tothemoon.common.capability;

import com.khanhtypo.tothemoon.ToTheMoon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;

public class PowerStorage extends EnergyStorage {
    private final int defaultCapacity;

    public PowerStorage(int capacity) {
        super(capacity);
        this.defaultCapacity = capacity;
    }

    @Override
    public boolean canExtract() {
        return super.canExtract() && !this.isEmpty();
    }

    @Override
    public boolean canReceive() {
        return super.canReceive() && !this.isFull();
    }

    public boolean isEmpty() {
        return super.getEnergyStored() <= 0;
    }

    public int getPowerSpace() {
        return Math.max(0, super.getMaxEnergyStored() - super.getEnergyStored());
    }

    public boolean isFull() {
        return super.getEnergyStored() >= super.getMaxEnergyStored();
    }

    public void setCapacity(int capacity) {
        super.capacity = Math.max(this.defaultCapacity, capacity);
    }

    public int getDefaultCapacity() {
        return defaultCapacity;
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("Amount", super.serializeNBT());
        if (super.capacity > this.defaultCapacity) {
            tag.putInt("Capacity", super.capacity);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(Tag energyTag) {
        @Nullable String failReason = null;
        if (energyTag != null) {
            if (energyTag instanceof CompoundTag energyCompound) {
                this.energy = energyCompound.getInt("Amount");
                if (energyCompound.contains("Capacity", CompoundTag.TAG_INT)) {
                    this.setCapacity(energyCompound.getInt("Capacity"));
                }
            } else {
                failReason = "Energy tag is not a compound tag.";
            }
        } else {
            failReason = "Energy tag is not present.";
        }

        if (failReason != null) {
            ToTheMoon.LOGGER.warn("Can not load Energy to a PowerStorage. " + failReason);
        }
    }

    public void setCapacityToDefault() {
        if (this.energy > this.defaultCapacity) {
            ToTheMoon.LOGGER.warn("(DEV, FIX NEEDED) There are energy that will be demolished when resetting capacity to default");
        }
        this.setCapacity(this.defaultCapacity);
    }
}
