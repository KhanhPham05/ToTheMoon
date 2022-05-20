package com.khanhpham.tothemoon.core.blockentities.others;

import com.khanhpham.tothemoon.core.blockentities.EnergyProcessBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelter;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterMenu;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.energy.EnergyReceivable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class EnergySmelterBlockEntity extends EnergyProcessBlockEntity {
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> workingTime;
                case 1 -> workingDuration;
                case 2 -> energy.getEnergyStored();
                case 3 -> energy.getMaxEnergyStored();
                default -> throw new ArrayIndexOutOfBoundsException();
            };
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public EnergySmelterBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public EnergySmelterBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.ENERGY_SMELTER.get(), pos, state, new EnergyReceivable(150000, 2000, 750), ModBlocks.ENERGY_SMELTER.get().getName(), 2);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, EnergySmelterBlockEntity e) {
        e.serverTick(level, blockPos, state);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new EnergySmelterMenu(containerId, playerInventory, this, this.data);
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return pSide == Direction.DOWN ? new int[1] : new int[0];
    }

    private void serverTick(Level level, BlockPos pos, BlockState state) {
        final SmeltingRecipe recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, this, level).orElse(null);
        ItemStack input = super.items.get(0);
        ItemStack result = super.items.get(1);

        if (isStillWorking()) {
            this.workingTime--;
        }

        if (this.workingDuration != 0 && this.workingTime <= 0) {
            super.trade(input, 1, recipe);
        }


        if (!energy.isEmpty() && !input.isEmpty()) {
            if (recipe != null && super.isResultSlotFreeForProcess(result, recipe)  && recipe.matches(this, level)) {
                this.workingTime = recipe.getCookingTime();
                this.workingDuration = this.workingTime;
                super.setNewBlockState(level, pos, state, EnergySmelter.WORKING, Boolean.TRUE);
            }
        } else {
            resetTime();
            super.setNewBlockState(level, pos, state, EnergySmelter.WORKING, Boolean.FALSE);
        }

        setChanged(level, pos, state);
    }
}
