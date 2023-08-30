package com.khanhtypo.tothemoon.common.item.upgrades;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;

public interface IUpgradeItem {
    UpgradeItemType getUpgradeType();
    void acceptUpgrade(AbstractMachineBlockEntity blockEntity);

    void onUpgradeTaken(AbstractMachineBlockEntity blockEntity);

    default boolean canTakeFromSlot(AbstractMachineBlockEntity blockEntity) {
        return true;
    }
}
