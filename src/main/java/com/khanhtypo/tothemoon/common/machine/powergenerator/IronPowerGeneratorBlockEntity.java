package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class IronPowerGeneratorBlockEntity extends PowerGeneratorBlockEntity {
    public IronPowerGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.IRON_POWER_GENERATOR, blockPos, blockState, 250_000, 125);
    }
}
