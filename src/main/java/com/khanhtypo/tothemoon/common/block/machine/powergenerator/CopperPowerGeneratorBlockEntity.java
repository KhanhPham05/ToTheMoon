package com.khanhtypo.tothemoon.common.block.machine.powergenerator;

import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CopperPowerGeneratorBlockEntity extends PowerGeneratorBlockEntity {
    public CopperPowerGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COPPER_POWER_GENERATOR, blockPos, blockState, 100_000, 50);
    }
}
