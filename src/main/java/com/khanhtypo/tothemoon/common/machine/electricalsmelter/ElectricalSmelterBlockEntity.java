package com.khanhtypo.tothemoon.common.machine.electricalsmelter;

import com.khanhtypo.tothemoon.common.machine.AbstractSingleItemProcessingMachineBlockEntity;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricalSmelterBlockEntity extends AbstractSingleItemProcessingMachineBlockEntity<ElectricalSmelterBlockEntity> {
    public ElectricalSmelterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ELECTRICAL_SMELTER, blockPos, blockState);
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new ElectricalSmelterMenu(
                pContainerId,
                pInventory,
                ContainerLevelAccess.create(super.getLevelOrThrow(), super.getBlockPos()),
                super.getContainer(),
                super.getUpgradeContainer(),
                super.getContainerData());
    }
}
