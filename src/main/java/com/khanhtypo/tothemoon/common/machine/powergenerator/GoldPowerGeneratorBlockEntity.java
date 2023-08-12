package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GoldPowerGeneratorBlockEntity extends PowerGeneratorBlockEntity {
    public GoldPowerGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.GOLD_POWER_GENERATOR, blockPos, blockState, 600_000, 225);
    }
}
