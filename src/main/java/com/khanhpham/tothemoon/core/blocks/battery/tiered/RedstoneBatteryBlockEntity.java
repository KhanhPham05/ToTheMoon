package com.khanhpham.tothemoon.core.blocks.battery.tiered;

import com.khanhpham.tothemoon.core.blocks.battery.AbstractBatteryBlockEntity;
import com.khanhpham.tothemoon.core.energy.BatteryEnergy;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneBatteryBlockEntity extends AbstractBatteryBlockEntity {
    public RedstoneBatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.REDSTONE_BATTERY.get(), pWorldPosition, pBlockState, new BatteryEnergy(REDSTONE_TIER_ENERGY), ModBlocks.REDSTONE_BATTERY.get());
    }
}
