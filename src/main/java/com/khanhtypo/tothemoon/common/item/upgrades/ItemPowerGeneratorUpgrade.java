package com.khanhtypo.tothemoon.common.item.upgrades;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorBlockEntity;

public interface ItemPowerGeneratorUpgrade extends IUpgradeItem {
    void acceptUpgrade(PowerGeneratorBlockEntity powerGenerator);

    @Override
    default void acceptUpgrade(AbstractMachineBlockEntity blockEntity) {
        if (blockEntity instanceof PowerGeneratorBlockEntity powerGenerator) {
            this.acceptUpgrade(powerGenerator);
        }
    }

    @Override
    default void onUpgradeTaken(AbstractMachineBlockEntity blockEntity) {
        if (blockEntity instanceof PowerGeneratorBlockEntity powerGenerator) {
            this.onUpgradeExtracted(powerGenerator);
        }
    }

    void onUpgradeExtracted(PowerGeneratorBlockEntity powerGenerator);
}
