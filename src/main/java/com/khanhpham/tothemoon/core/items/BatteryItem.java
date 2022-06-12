package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blockentities.battery.BatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.battery.BatteryBlock;

public class BatteryItem extends EnergyCapableItem {
    public BatteryItem(BatteryBlock block) {
        super(block, new Properties().tab(ToTheMoon.TAB));
    }

    @Override
    protected int getMaxEnergyStored() {
        return BatteryBlockEntity.ENERGY_CAPACITY;
    }


}
