package com.khanhpham.tothemoon.utils;

import com.khanhpham.tothemoon.core.abstracts.EnergyProcessBlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class MachineContainerData implements ContainerData {
    private final EnergyProcessBlockEntity blockEntity;

    public MachineContainerData(EnergyProcessBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public static SimpleContainerData simple() {
        return new SimpleContainerData(4);
    }

    @Override
    public int get(int pIndex) {
        return switch(pIndex) {
            case 0 -> blockEntity.workingTime;
            case 1 -> blockEntity.workingDuration;
            case 2 -> blockEntity.getEnergy().getEnergyStored();
            case 3 -> blockEntity.getEnergy().getMaxEnergyStored();
            default -> throw new IllegalStateException("Unexpected value: " + pIndex);
        };
    }

    @Override
    public void set(int pIndex, int pValue) {
       throw new UnsupportedOperationException();
    }

    @Override
    public int getCount() {
        return 4;
    }

    public int getEnergyBar() {
        int i = this.get(2);
        int j = this.get(3);

        return j != 0 && i != 0 ? i * 147 / j : 0;
    }

    public int getProcessBar() {
        int i = this.get(0);
        int j = this.get(1);
        return j != 0 && i != 0 ? i * 35 / j : 0;
    }

    public int getEnergyCap() {
        return this.get(3);
    }

    public int getEnergyStored() {
        return this.get(2);
    }
}
