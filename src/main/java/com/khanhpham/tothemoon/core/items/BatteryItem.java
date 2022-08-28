package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlockEntity;
import net.minecraft.world.level.block.Block;

public class BatteryItem extends EnergyCapableItem {
    private final int energyCapacity;

    public BatteryItem(Block block, int energyCapacity) {
        super(block, new Properties().tab(ToTheMoon.TAB));
        this.energyCapacity = energyCapacity;
    }

    @Override
    protected int getMaxEnergyStored() {
        return this.energyCapacity;
    }
}
