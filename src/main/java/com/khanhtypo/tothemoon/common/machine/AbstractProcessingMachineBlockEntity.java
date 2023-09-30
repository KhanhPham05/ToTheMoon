package com.khanhtypo.tothemoon.common.machine;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.common.capability.PowerStorage;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public abstract class AbstractProcessingMachineBlockEntity<T extends AbstractProcessingMachineBlockEntity<T>> extends AbstractMachineBlockEntity<T> {
    protected static final int defaultDurationSeconds = 10;
    protected int seconds;
    protected int durationSeconds;
    public AbstractProcessingMachineBlockEntity(BlockEntityObject<T> blockEntity, BlockPos blockPos, BlockState blockState, int containerSize, PowerStorage energyStorage, Function<T, ContainerData> dataConstructor) {
        super(blockEntity, blockPos, blockState, containerSize, energyStorage, dataConstructor);
    }

    public int getSeconds() {
        return seconds;
    }
}
