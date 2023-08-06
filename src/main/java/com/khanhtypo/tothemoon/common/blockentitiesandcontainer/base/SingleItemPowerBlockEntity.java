package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class SingleItemPowerBlockEntity extends AbstractPowerBlockEntity {
    public static final int CONTAINER_SIZE = 1;
    protected int energyProcessDuration;
    protected int energyProcessTime;

    public SingleItemPowerBlockEntity(BlockEntityObject<? extends AbstractPowerBlockEntity> blockEntity, BlockPos blockPos, BlockState blockState, EnergyStorage energyStorage) {
        super(blockEntity, blockPos, blockState, CONTAINER_SIZE, energyStorage);
    }

    @Override
    protected LazyOptional<IItemHandler> getHandlerForEachFace(Direction side) {
        return super.itemHolder;
    }

    @Override
    public int[] getSlotsForFace(Direction p_19238_) {
        return new int[]{0};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction side) {
        return side != Direction.DOWN && !this.isEmpty() && !itemStack.isEmpty();
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction side) {
        return side == Direction.DOWN && !this.isEmpty() && !itemStack.isEmpty();
    }

    public void loadPowerConsumeProcess(CompoundTag deserializedNBT) {
        this.energyProcessDuration = deserializedNBT.getInt("EnergyProcessDuration");
        this.energyProcessTime = deserializedNBT.getInt("EnergyProcessTime");
    }

    protected void savePowerConsumeProcess(CompoundTag writer) {
        writer.putInt("EnergyProcessDuration", this.energyProcessDuration);
        writer.putInt("EnergyProcessTime", this.energyProcessTime);
    }

    protected int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }

    protected int getBurnTime(int slot) {
        return this.getBurnTime(super.getItem(slot));
    }

    protected boolean canBurn(ItemStack stack) {
        return this.getBurnTime(stack) > 0;
    }

    protected boolean canBurn(int slot) {
        return this.canBurn(super.getItem(slot));
    }

}
