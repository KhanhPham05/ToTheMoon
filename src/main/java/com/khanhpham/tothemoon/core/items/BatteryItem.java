package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlockEntity;
import net.minecraft.world.level.block.Block;

public class BatteryItem extends EnergyCapableItem {
    public BatteryItem(Block block) {
        super(block, new Properties().tab(ToTheMoon.TAB));
    }

    @Override
    protected int getMaxEnergyStored() {
        return BatteryBlockEntity.ENERGY_CAPACITY;
    }
}
