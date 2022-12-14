package com.khanhpham.tothemoon.core.abstracts;

import com.khanhpham.tothemoon.core.energy.Energy;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class EnergyProcessBlockEntity extends EnergyItemCapableBlockEntity {
    public int workingSpeedModify = 1;
    public int workingTime;
    public int workingDuration = 100;

    public EnergyProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, Supplier<? extends Block> block, int containerSize) {
        this(pType, pWorldPosition, pBlockState, energy, block.get().getName(), containerSize);
    }

    public EnergyProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize + 4);
    }

    protected boolean isStillWorking() {
        return this.workingTime < this.workingDuration;
    }

    protected boolean isIdle() {
        return this.workingTime == 0;
    }

    protected boolean isResultSlotFreeForProcess(int slotIndex, @Nullable Recipe<? extends Container> recipe) {
        return recipe != null && ModUtils.isSlotFree(this, slotIndex, recipe.getResultItem());
    }

    protected void modifyTime() {
        this.workingTime += workingSpeedModify;
        super.energy.consumeEnergyIgnoreCondition();
    }

    protected void resetTime() {
        this.workingTime = 0;
        //this.workingDuration = 0;
    }

    protected BlockState resetTime(Level level, BlockPos pos, BlockState state, BooleanProperty lit) {
        this.resetTime();
        return setNewBlockState(level, pos, state, lit, false);
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

    protected void processResult(int resultIndex, ItemStack stack) {
        if (!stack.isEmpty()) {
            ItemStack resultSlot = this.items.get(resultIndex);
            if (resultSlot.isEmpty()) {
                this.items.set(resultIndex, stack);
            } else if (resultSlot.sameItem(stack)) {
                int space = getMaxStackSize() - resultSlot.getCount();
                this.items.get(resultIndex).grow(Math.min(space, stack.getCount()));
            }
        }
    }
}
