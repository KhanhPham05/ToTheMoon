package com.khanhpham.tothemoon.util.energy;

@Deprecated
public interface IEnergySource extends IHasEnergy{
    default int getEnergy(int maxReceive) {
        int maxReceiveInternal = getEnergyStorage().getMaxReceive();

        if (maxReceive > maxReceiveInternal) {
            this.getEnergyStorage().receiveEnergy(maxReceiveInternal, false);
            return maxReceive;
        }

        this.getEnergyStorage().receiveEnergy(maxReceive, false);
        return maxReceive;
    }
}
