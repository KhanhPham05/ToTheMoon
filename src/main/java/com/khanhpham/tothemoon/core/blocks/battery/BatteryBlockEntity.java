package com.khanhpham.tothemoon.core.blocks.battery;

import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.core.energy.BatteryEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryBlockEntity extends AbstractBatteryBlockEntity {
    public static final int ENERGY_CAPACITY = 250_000;

    public BatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.BATTERY.get(), pWorldPosition, pBlockState, new BatteryEnergy(ENERGY_CAPACITY), ModBlocks.BATTERY.get().getName());
    }
}
