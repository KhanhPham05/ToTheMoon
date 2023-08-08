package com.khanhtypo.tothemoon.common.block.machine.powergenerator;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SingleItemPowerBlockEntity;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class NetheritePowerGeneratorBlockEntity extends PowerGeneratorBlockEntity {
    public NetheritePowerGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.NETHERITE_POWER_GENERATOR, blockPos, blockState, 1_500_000, 800);
    }

    public NetheritePowerGeneratorBlockEntity(BlockEntityObject<? extends SingleItemPowerBlockEntity> blockEntity, BlockPos blockPos, BlockState blockState, int powerCapacity, int generationPerTick) {
        super(blockEntity, blockPos, blockState, powerCapacity, generationPerTick);
    }
}
