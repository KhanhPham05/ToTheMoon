package com.khanhpham.tothemoon.core.blocks.machines.oreprocessor;

import com.khanhpham.tothemoon.core.abstracts.EnergyProcessBlockEntity;
import com.khanhpham.tothemoon.core.energy.EnergyOnlyReceive;
import com.khanhpham.tothemoon.core.recipes.OreProcessingRecipe;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OreProcessorBlockEntity extends EnergyProcessBlockEntity {
    public static final int CONTAINER_SIZE = 4;
    public static final int DATA_SIZE = 4;
    private final ContainerData data;

    //0 : main slot
    //1 : queue slot

    //2 : result
    //3 : extra result
    public OreProcessorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.ENERGY_PROCESSOR.get(), pWorldPosition, pBlockState, new EnergyOnlyReceive(200000, 250), ModBlocks.ORE_PROCESSOR, CONTAINER_SIZE);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> workingTime;
                    case 1 -> workingDuration;
                    case 2 -> energy.getEnergyStored();
                    case 3 -> energy.getMaxEnergyStored();
                    default -> throw new IllegalStateException("Unexpected value: " + pIndex);
                };
            }

            @Override
            public void set(int pIndex, int pValue) {

            }

            @Override
            public int getCount() {
                return DATA_SIZE;
            }
        };
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return pIndex <= 1;
    }

    @Override
    protected boolean canTakeItem(int index) {
        return !canPlaceItem(index, ItemStack.EMPTY);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new OreProcessorMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (this.items.get(0).isEmpty() && !this.items.get(1).isEmpty()) {
            ItemStack queueStack = super.items.get(1);
            super.items.set(0, queueStack);
            super.items.set(1, ItemStack.EMPTY);
        }

        if (!super.items.get(0).isEmpty() && !super.energy.isEmpty()) {
            @Nullable OreProcessingRecipe recipe = ModUtils.getRecipe(level, OreProcessingRecipe.RECIPE_TYPE, this);

            if (recipe != null) {
                if (this.workingDuration > 0) {
                    if (this.workingTime < this.workingDuration) {
                        super.modifyTime();
                    } else {
                        this.process(recipe);
                    }
                } else {
                    super.workingDuration = recipe.getProcessingTime();
                    super.workingTime = 0;
                    state = super.setNewBlockState(level, pos, state, OreProcessorBlock.LIT, true);
                }
            } else state = super.resetTime(level, pos, state, OreProcessorBlock.LIT);
        } else {
            state = super.resetTime(level, pos, state, OreProcessorBlock.LIT);
        }

        setChanged(level, pos, state);
    }




    private void process(@Nonnull OreProcessingRecipe recipe) {
        super.resetTime();
        super.processResult(2, recipe.assemble(this));
        super.processResult(3, recipe.giveExtra(this));
        super.items.get(0).shrink(1);
    }

    public boolean isResultFree(OreProcessingRecipe recipe) {
        return this.isResultSlotFreeForProcess(2, recipe);
    }

}
