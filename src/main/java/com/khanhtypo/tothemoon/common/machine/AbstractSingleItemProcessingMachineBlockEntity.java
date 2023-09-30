package com.khanhtypo.tothemoon.common.machine;

import com.khanhtypo.tothemoon.common.capability.PowerStorage;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSingleItemProcessingMachineBlockEntity<T extends AbstractSingleItemProcessingMachineBlockEntity<T>> extends AbstractProcessingMachineBlockEntity<T> {
    public static final int POWER_CAP = 500_000;
    public static final int DATA_COUNT = 8;

    public AbstractSingleItemProcessingMachineBlockEntity(BlockEntityObject<T> blockEntityObject, BlockPos blockPos, BlockState blockState) {
        super(blockEntityObject, blockPos, blockState, 2, new PowerStorage(POWER_CAP),
                machine -> new ContainerData() {
                    @Override
                    public int get(int pIndex) {
                        return switch (pIndex) {
                            case 0 -> machine.energyStorage.getEnergyStored();
                            case 1 -> machine.energyStorage.getMaxEnergyStored();
                            case 2 -> machine.seconds;
                            case 3 -> machine.durationSeconds;
                            case 4 -> machine.fuelConsumeTime;
                            case 5 -> machine.fuelConsumeDuration;
                            case 6 -> machine.redstoneMode.getIndex();
                            case 7 -> machine.active ? 1 : 0;
                            default -> throw new IllegalStateException("Unexpected value: " + pIndex);
                        };
                    }

                    @Override
                    public void set(int pIndex, int pValue) {
                        switch (pIndex) {
                            case 6 -> machine.redstoneMode = MachineRedstoneMode.valueFromIndex(pValue);
                            case 7 -> machine.active = pValue == 1;
                        }
                    }

                    @Override
                    public int getCount() {
                        return DATA_COUNT;
                    }
                });
    }

    @Override
    protected void tick(Level level, BlockPos pos, BlockState blockState) {

    }

    @Override
    protected void saveAdditional(CompoundTag writer) {
        super.saveAdditional(writer);

    }

    @Override
    public void load(CompoundTag deserializedNBT) {
        super.load(deserializedNBT);

    }
}