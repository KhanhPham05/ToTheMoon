package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.common.capability.PowerStorage;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

@Deprecated
public abstract class SingleItemMachineBlockEntity extends AbstractMachineBlockEntity {
    public static final int CONTAINER_SIZE = 1;
    protected int energyProcessDuration;
    protected int energyProcessTime;

    public SingleItemMachineBlockEntity(BlockEntityObject<? extends AbstractMachineBlockEntity> blockEntity, BlockPos blockPos, BlockState blockState, PowerStorage energyStorage, Function<AbstractMachineBlockEntity, ContainerData> dataConstructor) {
        super(blockEntity, blockPos, blockState, CONTAINER_SIZE, energyStorage, dataConstructor);
    }

    public void loadPowerConsumeProcess(CompoundTag deserializedNBT) {
        this.energyProcessDuration = deserializedNBT.getInt("EnergyProcessDuration");
        this.energyProcessTime = deserializedNBT.getInt("EnergyProcessTime");
    }

    protected void savePowerConsumeProcess(CompoundTag writer) {
        writer.putInt("EnergyProcessDuration", this.energyProcessDuration);
        writer.putInt("EnergyProcessTime", this.energyProcessTime);
    }

}
