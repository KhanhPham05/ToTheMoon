package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.impl;

import com.khanhtypo.tothemoon.common.block.FunctionalBlock;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SingleItemEnergyBlockEntity;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SuppliedContainerData;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus.PowerGeneratorMenu;
import com.khanhtypo.tothemoon.common.capability.GeneratableEnergyStorage;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public abstract class AbstractPowerGeneratorBlockEntity extends SingleItemEnergyBlockEntity {
    protected final GeneratableEnergyStorage energyStorage;
    protected final SuppliedContainerData containerData;
    private final int generationPerTick;
    private int burningTime;
    private int burningDuration;

    public AbstractPowerGeneratorBlockEntity(BlockEntityObject<? extends SingleItemEnergyBlockEntity> blockEntity, BlockPos blockPos, BlockState blockState, int powerCapacity, int generationPerTick) {
        super(blockEntity, blockPos, blockState, new GeneratableEnergyStorage(powerCapacity));
        this.generationPerTick = generationPerTick;
        this.energyStorage = ((GeneratableEnergyStorage) super.energyStorage);
        this.containerData = new SuppliedContainerData(this.energyStorage::getEnergyStored, this.energyStorage::getMaxEnergyStored, this::getBurningTime, this::getBurningDuration, this::getGenerationPerTick);
    }

    @Override
    protected void tick(Level level, BlockPos pos, BlockState blockState) {
        boolean litState = blockState.getValue(FunctionalBlock.LIT);
        if (this.burningTime > 0) {
            burningTime--;
            this.energyStorage.generatePower(this.generationPerTick);
        } else if (this.isSlotPresent(0) && super.canBurn(0)) {
            this.burningTime = this.burningDuration = super.getBurnTime(0);
            super.shrinkItem(0, 1);
            litState = true;
        } else litState = false;

        blockState = blockState.setValue(FunctionalBlock.LIT, litState);
        level.setBlock(pos, blockState, 3);
    }

    private int getBurningTime() {
        return this.burningTime;
    }

    public int getBurningDuration() {
        return this.burningDuration;
    }

    public int getGenerationPerTick() {
        return this.generationPerTick;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        Level level = Objects.requireNonNull(this.getLevel());
        return new PowerGeneratorMenu(pContainerId, pInventory, ContainerLevelAccess.create(level, this.getBlockPos()), this, this.containerData)
                .setTargetedBlock(super.getBlockState().getBlock());
    }

    @Override
    protected void saveAdditional(CompoundTag writer) {
        super.saveAdditional(writer);
        writer.putInt("BurningTime", this.burningTime);
        writer.putInt("BurningDuration", this.burningDuration);
    }

    @Override
    public void load(CompoundTag deserializedNBT) {
        super.load(deserializedNBT);
        this.burningTime = deserializedNBT.getInt("BurningTime");
        this.burningDuration = deserializedNBT.getInt("BurningDuration");
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return super.canBurn(pStack);
    }
}
