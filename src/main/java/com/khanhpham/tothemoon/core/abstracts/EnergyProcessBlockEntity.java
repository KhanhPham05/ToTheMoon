package com.khanhpham.tothemoon.core.abstracts;

import com.khanhpham.tothemoon.core.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class EnergyProcessBlockEntity extends EnergyItemCapableBlockEntity {
    @Deprecated
    public int workingSpeedModify = 1;
    protected int workingTime;
    protected int workingDuration = 100;


    public EnergyProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize + 4);
    }

    protected boolean isStillWorking() {
        return this.workingTime < this.workingDuration;
    }

    protected boolean isIdle() {
        return this.workingTime == 0;
    }

    protected <C extends Container> boolean isResultSlotFreeForProcess(ItemStack stack, @Nullable Recipe<C> recipe) {
        if (recipe != null) {
            if (stack.isEmpty()) {
                return true;
            } else
                return stack.sameItem(recipe.getResultItem()) && stack.getCount() + recipe.getResultItem().getCount() <= this.getMaxStackSize();
        }

        return false;
    }

    protected void resetTime() {
        this.workingTime = 0;
        this.workingDuration = 0;
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
