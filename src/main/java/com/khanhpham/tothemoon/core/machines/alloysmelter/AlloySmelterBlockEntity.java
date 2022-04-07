package com.khanhpham.tothemoon.core.machines.alloysmelter;

import com.khanhpham.tothemoon.core.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.ModUtils;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.energy.EnergyReceivable;
import com.khanhpham.tothemoon.utils.te.EnergyProcessBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlloySmelterBlockEntity extends EnergyProcessBlockEntity {
    public static final int MENU_SIZE = 3;
    public static final TranslatableComponent LABEL = ModUtils.translate("gui.tothemoon.alloy_smelter");

    public AlloySmelterBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public AlloySmelterBlockEntity(BlockPos blockPos, BlockState state) {
        this(ModBlockEntityTypes.ALLOY_SMELTER.get(), blockPos, state, new EnergyReceivable(175000, 750, 500), LABEL, MENU_SIZE);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlloySmelterBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }

    /**
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity#serverTick(Level, BlockPos, BlockState, AbstractFurnaceBlockEntity)
     */
    private void serverTick(Level level, BlockPos pos, BlockState state) {
        receiveEnergyFromOther(level, pos);

        @Nullable
        AlloySmeltingRecipe recipe = super.getRecipe(level, AlloySmeltingRecipe.RECIPE_TYPE, this);

        if (recipe != null) {
            if (this.workingDuration != 0 && this.workingTime >= workingDuration) {
                this.exchangeInputsWithOutput(recipe);
            }

            if (!this.energy.isEmpty()) {

                if (super.isStillWorking()) {
                    this.workingTime++;
                    this.energy.consumeEnergyIgnoreCondition();
                }


                if (this.isIdle() & super.isResultSlotFreeForProcess(this.items.get(2), recipe)) {
                    if (recipe.matches(this, level)) {
                        this.workingTime = 0;
                        this.workingDuration = recipe.getAlloyingTime();
                        state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.TRUE);
                    }
                }
            } else {
                if (this.workingTime >= 0) {
                    this.workingTime--;
                }

                state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.FALSE);
            }
        } else {
            this.resetTime();
            state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.FALSE);
        }

        setChanged(level, pos, state);
    }

    private void exchangeInputsWithOutput(@Nonnull AlloySmeltingRecipe recipe) {
        this.items.get(0).shrink(recipe.baseIngredient.amount);
        this.items.get(1).shrink(recipe.secondaryIngredient.amount);
        if (this.items.get(2).isEmpty()) {
            this.items.set(2, recipe.assemble(this).copy());
        } else {
            this.items.get(2).grow(recipe.result.getCount());
        }

        this.resetTime();
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new AlloySmelterMenu(containerId, playerInventory, this, super.data);
    }
}
