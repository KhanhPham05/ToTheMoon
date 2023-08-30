package com.khanhtypo.tothemoon.common.item.upgrades;

import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorBlockEntity;

public final class SpeedUpgradeItem extends AbstractUpgradeItem implements ItemPowerGeneratorUpgrade {
    public SpeedUpgradeItem(UpgradeItemType upgradeType, int tier) {
        super(upgradeType, tier);
    }

    @Override
    public void acceptUpgrade(PowerGeneratorBlockEntity powerGenerator) {
        powerGenerator.setFuelConsumeDuration(powerGenerator.getDefaultFuelConsumeDuration() - (super.tier * 5));
    }

    @Override
    public void onUpgradeExtracted(PowerGeneratorBlockEntity powerGenerator) {
        powerGenerator.setFuelConsumeDuration(PowerGeneratorBlockEntity.DEFAULT_FUEL_CONSUME_DURATION);
    }
}
