package com.khanhpham.tothemoon.core.blocks.machines.battery.creative;

import com.khanhpham.tothemoon.core.blockentities.battery.AbstractBatteryBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.energy.BatteryEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeBatteryBlockEntity extends AbstractBatteryBlockEntity {
    public CreativeBatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.BATTERY.get(), pWorldPosition, pBlockState, new BatteryEnergy(500000000, 100000, 100000, BatteryEnergy.BatteryEnergyType.ALWAYS_FULL), ModBlocks.CREATIVE_BATTERY.get().getName(), CONTAINER_SIZE);
    }
}
