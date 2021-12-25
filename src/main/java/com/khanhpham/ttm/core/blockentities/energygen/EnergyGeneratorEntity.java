package com.khanhpham.ttm.core.blockentities.energygen;

import com.khanhpham.ttm.init.ModMisc;
import com.khanhpham.ttm.testfeatures.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyGeneratorEntity extends TickableBlockEntity {
    public EnergyGeneratorEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModMisc.ENERGY_GEN_ENTITY ,pWorldPosition, pBlockState);
    }
}
