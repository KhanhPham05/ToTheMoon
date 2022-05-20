package com.khanhpham.tothemoon.core.blockentities.battery;

import com.khanhpham.tothemoon.core.blockentities.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.energy.BatteryEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryBlockEntity extends AbstractBatteryBlockEntity {
    public BatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.BATTERY.get(), pWorldPosition, pBlockState, new BatteryEnergy(75000, 2000, 2500), ModBlocks.BATTERY.get().getName(), CONTAINER_SIZE);
    }
}
