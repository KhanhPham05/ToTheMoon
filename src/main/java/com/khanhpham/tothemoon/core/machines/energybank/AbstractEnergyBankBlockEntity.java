package com.khanhpham.tothemoon.core.machines.energybank;

import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.te.EnergyCapableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AbstractEnergyBankBlockEntity extends EnergyCapableTileEntity {
    public AbstractEnergyBankBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy) {
        super(pType, pWorldPosition, pBlockState, energy);
    }
}
