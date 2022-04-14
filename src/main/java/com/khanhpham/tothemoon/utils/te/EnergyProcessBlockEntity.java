package com.khanhpham.tothemoon.utils.te;

import com.khanhpham.tothemoon.utils.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;

public abstract class EnergyProcessBlockEntity extends EnergyItemCapableBlockEntity {
    protected int workingTime;
    protected int workingDuration;

    public EnergyProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
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
