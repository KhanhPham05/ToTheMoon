package com.khanhpham.tothemoon.core.machines.metalpress;

import com.khanhpham.tothemoon.core.recipes.MetalPressingRecipe;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.energy.EnergyReceivable;
import com.khanhpham.tothemoon.utils.te.EnergyProcessBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class MetalPressBlockEntity extends EnergyProcessBlockEntity {
    public static final int MENU_SIZE = 3;
    private int workingTime;
    private int workingDuration;
    public static final int DATA_SIZE = 4;

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0-> workingTime;
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
            return 4;
        }
    };

    public MetalPressBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public MetalPressBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntityTypes.METAL_PRESS.get(), blockPos, blockState, new EnergyReceivable(175000, 5000, 2000), new TranslatableComponent("gui.tothemoon.metal_press"), MENU_SIZE);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState state, MetalPressBlockEntity e) {
        e.serverTick(level, blockPos, state);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new MetalPressMenu(this, playerInventory, containerId, this.data);
    }

    //TODO : fix Processing bug
    private void serverTick(Level level, BlockPos pos, BlockState state) {
        super.receiveEnergyFromOther(level, pos);

        @Nullable
        MetalPressingRecipe recipe = super.getRecipe(level, MetalPressingRecipe.RECIPE_TYPE, this);

        if (recipe != null) {
            if (this.workingDuration != 0 && this.workingTime >= this.workingDuration) {
                this.exchangeInputWithResults(recipe);
            }

            if (!super.energy.isEmpty()) {

                if (super.isStillWorking()) {
                    this.workingTime++;
                    super.energy.consumeEnergyIgnoreCondition();
                }

                if (super.isIdle() && super.isResultSlotFreeForProcess(super.items.get(2), recipe)) {
                    if (recipe.matches(this, level)) {
                        this.workingTime = 0;
                        this.workingDuration = recipe.getPressingTime();
                        state = super.setNewBlockState(level, pos, state, MetalPressBlock.LIT, Boolean.TRUE);
                    }
                }
            } else {
                if (this.workingTime >= 0) {
                    this.workingTime--;
                }

                state = super.setNewBlockState(level, pos, state, MetalPressBlock.LIT, Boolean.FALSE);
            }
        } else {
            super.resetTime();
            state = super.setNewBlockState(level, pos, state, MetalPressBlock.LIT, Boolean.FALSE);
        }

        setChanged(level, pos, state);
    }

    private void exchangeInputWithResults(@Nonnull MetalPressingRecipe recipe) {
        super.items.get(0).shrink(1);
        if (super.items.get(2).isEmpty()) {
            super.items.set(2, recipe.assemble(this).copy());
        } else {
            this.items.get(2).grow(1);
        }
        this.resetTime();
    }
}
