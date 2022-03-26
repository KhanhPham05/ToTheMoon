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
    protected final Data data = new Data(workingTime, workingDuration, energy.getEnergyStored(), energy.getMaxEnergyStored());

    public EnergyProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    protected void addData(int value) {
        this.data.addData(value);
    }

    protected boolean isStillWorking() {
        return this.workingTime >= this.workingDuration;
    }

    protected boolean isIdle() {
        return this.workingDuration == 0 && this.workingTime == 0;
    }

    protected <C extends Container> boolean isResultSlotFreeForProcess(ItemStack stack, @Nullable Recipe<C> recipe) {
        if (recipe != null) {
            if (stack.isEmpty()) {
                return true;
            } else
                return stack.is(recipe.getResultItem().getItem()) && stack.getCount() <= this.getMaxStackSize() - recipe.getResultItem().getCount();
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

    private static final class Data implements ContainerData {

        private int[] values;

        public Data(int... values) {
            this.values = values;
        }

        private void addData(int value) {
            int length = this.values.length;
            int[] oldArray = this.values;
            int[] newArray = Arrays.copyOf(oldArray, length++);
            newArray[length] = value;
            this.values = newArray;
        }

        @Override
        public int get(int pIndex) {
            return values[pIndex];
        }

        @Override
        public void set(int pIndex, int pValue) {
            this.values[pIndex] = pValue;
        }

        @Override
        public int getCount() {
            return values.length;
        }
    }
}
