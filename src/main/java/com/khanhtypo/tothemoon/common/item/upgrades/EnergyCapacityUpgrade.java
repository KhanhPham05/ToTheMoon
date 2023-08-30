package com.khanhtypo.tothemoon.common.item.upgrades;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.common.capability.PowerStorage;
import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorBlockEntity;
import com.khanhtypo.tothemoon.utls.AppendableComponent;

public class EnergyCapacityUpgrade extends AbstractUpgradeItem implements ItemPowerGeneratorUpgrade {
    public static final AppendableComponent CAN_NOT_TAKE_UPGRADE_MESSAGE_TEMPLATE = AppendableComponent.create("message", "can_not_take_energy_upgrade", "Can not take %s, %s FE will be lost");

    //15% -> 25% -> 40%
    public EnergyCapacityUpgrade(UpgradeItemType upgradeType, int tier) {
        super(upgradeType, tier);
    }

    @Override
    public void acceptUpgrade(PowerGeneratorBlockEntity powerGenerator) {
        PowerStorage energyStorage = powerGenerator.energyStorage;
        int defaultCapacity = energyStorage.getDefaultCapacity();
        int energyEfficiency = this.getCapacityEff();
        energyStorage.setCapacity(
                defaultCapacity + (int) (((float) (defaultCapacity / 100)) * energyEfficiency)
        );
    }

    private int getCapacityEff() {
        return switch (super.tier) {
            case 1 -> 15;
            case 2 -> 25;
            case 3 -> 40;
            default -> throw new IllegalStateException("Unexpected value: " + super.tier);
        };
    }

    @Override
    public void onUpgradeExtracted(PowerGeneratorBlockEntity powerGenerator) {
        powerGenerator.energyStorage.setCapacityToDefault();
    }

    @Override
    public boolean canTakeFromSlot(AbstractMachineBlockEntity blockEntity) {
        PowerStorage powerStorage = blockEntity.getEnergyStorage();

        if (powerStorage.getEnergyStored() > powerStorage.getDefaultCapacity()) {
            int spareEnergy = powerStorage.getEnergyStored() - powerStorage.getDefaultCapacity();
            if (!blockEntity.getLevel().isClientSide()) {
                blockEntity.getUpgradeContainer().openingPlayers.forEach(player -> player.displayClientMessage(CAN_NOT_TAKE_UPGRADE_MESSAGE_TEMPLATE.withParam(super.getDescription(), spareEnergy), false));
            }
            return false;
        }

        return true;
    }
}
