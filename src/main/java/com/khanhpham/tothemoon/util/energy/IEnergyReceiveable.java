package com.khanhpham.tothemoon.util.energy;

import com.khanhpham.tothemoon.api.block.TTMEnergyStorage;

@Deprecated
public interface IEnergyReceiveable extends IHasEnergy {
    default int removeEnergy(TTMEnergyStorage externalEnergy, int maxRemove) {
        int maxExtractInternal = getEnergyStorage().getMaxExtract();

        if (maxRemove > maxExtractInternal) {
            this.getEnergyStorage().extractEnergy(maxExtractInternal, false);
            return externalEnergy.receiveEnergy(maxExtractInternal, false);
        }

        this.getEnergyStorage().extractEnergy(maxRemove, false);
        return externalEnergy.receiveEnergy(maxRemove, false);
    }
}
