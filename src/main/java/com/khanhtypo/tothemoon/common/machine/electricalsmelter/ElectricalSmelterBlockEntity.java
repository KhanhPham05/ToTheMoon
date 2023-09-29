package com.khanhtypo.tothemoon.common.machine.electricalsmelter;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.common.capability.PowerStorage;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricalSmelterBlockEntity extends AbstractMachineBlockEntity {
    public static final int POWER_CAP = 500_000;
    public ElectricalSmelterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ELECTRICAL_SMELTER, blockPos, blockState, 2, new PowerStorage(POWER_CAP),
                machine -> new ContainerData() {
                    @Override
                    public int get(int pIndex) {
                        return switch (pIndex){
                            case 0 -> machine.energyStorage.getEnergyStored();
                            case 1 -> machine.energyStorage.getMaxEnergyStored();

                            default -> throw new IllegalStateException("Unexpected value: " + pIndex);
                        };

                    }

                    @Override
                    public void set(int pIndex, int pValue) {

                    }

                    @Override
                    public int getCount() {
                        return 0;
                    }
                });
    }

    @Override
    protected void tick(Level level, BlockPos pos, BlockState blockState) {

    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }
}