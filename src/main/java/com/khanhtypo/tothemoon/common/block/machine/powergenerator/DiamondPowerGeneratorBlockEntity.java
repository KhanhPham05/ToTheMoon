package com.khanhtypo.tothemoon.common.block.machine.powergenerator;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SingleItemPowerBlockEntity;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DiamondPowerGeneratorBlockEntity extends PowerGeneratorBlockEntity  {
    public DiamondPowerGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.DIAMOND_POWER_GENERATOR, blockPos, blockState, 1_000_000, 500);
    }

    public DiamondPowerGeneratorBlockEntity(BlockEntityObject<? extends SingleItemPowerBlockEntity> blockEntity, BlockPos blockPos, BlockState blockState, int powerCapacity, int generationPerTick) {
        super(blockEntity, blockPos, blockState, powerCapacity, generationPerTick);
    }
}
