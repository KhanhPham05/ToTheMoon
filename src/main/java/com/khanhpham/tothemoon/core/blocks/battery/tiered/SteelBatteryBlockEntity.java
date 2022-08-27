package com.khanhpham.tothemoon.core.blocks.battery.tiered;

import com.khanhpham.tothemoon.core.blocks.battery.AbstractBatteryBlockEntity;
import com.khanhpham.tothemoon.core.energy.BatteryEnergy;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SteelBatteryBlockEntity extends AbstractBatteryBlockEntity {
    public SteelBatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.STEEL_BATTERY.get(), pWorldPosition, pBlockState, new BatteryEnergy(STEEL_TIER_CAPACITY), ModBlocks.STEEL_BATTERY.get());
    }
}
