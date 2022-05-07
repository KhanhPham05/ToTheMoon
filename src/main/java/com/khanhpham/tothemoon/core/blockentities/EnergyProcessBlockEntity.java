package com.khanhpham.tothemoon.core.blockentities;

import com.khanhpham.tothemoon.utils.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class EnergyProcessBlockEntity extends EnergyItemCapableBlockEntity implements WorldlyContainer {
    protected int workingTime;
    protected int workingDuration;

    public EnergyProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    private LazyOptional<? extends IItemHandler>[] handler = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
        if (!super.remove && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.UP) {
                return handler[0].cast();
            } else if (side == Direction.DOWN) {
                return handler[1].cast();
            } else return handler[2].cast();
        }
        return super.getCapability(cap, side);
    }



    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, Direction pDirection) {
        return pDirection != Direction.DOWN;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return pDirection == Direction.DOWN;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> lazyOptional : handler) {
            lazyOptional.invalidate();
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handler = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }

    protected boolean isStillWorking() {
        return this.workingTime < this.workingDuration && this.workingDuration != 0;
    }

    protected boolean isIdle() {
        return this.workingDuration <= 0 && this.workingTime == 0;
    }

    protected <C extends Container> boolean isResultSlotFreeForProcess(ItemStack stack, @Nullable Recipe<C> recipe) {
        if (recipe != null) {
            if (stack.isEmpty()) {
                return true;
            } else
                return stack.is(recipe.getResultItem().getItem()) && stack.getCount() + recipe.getResultItem().getCount() <= this.getMaxStackSize();
        }

        return false;
    }

    protected void resetTime() {
        this.workingDuration = 0;
        this.workingTime = 0;
    }

    @Override
    protected final void saveExtra(CompoundTag tag) {
        tag.putInt("workingTime", this.workingTime);
        tag.putInt("workingDuration", this.workingDuration);
    }

    @Override
    protected final void loadExtra(CompoundTag tag) {
        this.workingDuration = tag.getInt("workingDuration");
        this.workingTime = tag.getInt("workingTime");
    }
}
