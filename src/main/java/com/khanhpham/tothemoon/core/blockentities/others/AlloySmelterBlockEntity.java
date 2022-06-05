package com.khanhpham.tothemoon.core.blockentities.others;

import com.khanhpham.tothemoon.core.abstracts.EnergyProcessBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterBlock;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenu;
import com.khanhpham.tothemoon.core.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.energy.EnergyReceivable;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AlloySmelterBlockEntity extends EnergyProcessBlockEntity implements WorldlyContainer {
    public static final int MENU_SIZE = 3;
    public static final TranslatableComponent LABEL = ModUtils.translate("gui.tothemoon.alloy_smelter");

    public final ContainerData data = new ContainerData() {
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
            return 4;
        }
    };

    public AlloySmelterBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public AlloySmelterBlockEntity(BlockPos blockPos, BlockState state) {
        this(ModBlockEntityTypes.ALLOY_SMELTER.get(), blockPos, state, new EnergyReceivable(175000, 750, 500), LABEL, MENU_SIZE);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlloySmelterBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }


    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (!super.isEmpty(0) || !super.isEmpty(1)) {
            @Nullable AlloySmeltingRecipe recipe = super.getRecipe(level, AlloySmeltingRecipe.RECIPE_TYPE, this);
            if (recipe != null) {
                if (!this.energy.isEmpty()) {
                    if (super.isStillWorking()) {
                        this.workingTime++;
                        this.energy.consumeEnergyIgnoreCondition();
                    } else if (super.workingDuration != 0) this.exchangeInputsWithOutput(recipe);

                    if (this.isIdle() & super.isResultSlotFreeForProcess(this.items.get(2), recipe)) {
                        if (recipe.matches(this, level)) {
                            this.workingTime = 0;
                            this.workingDuration = recipe.getAlloyingTime();
                            state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.TRUE);
                        }
                    }
                } else {
                    this.resetTime();
                    state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.FALSE);
                }
            } else {
                this.resetTime();
                state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.FALSE);
            }
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
        return new AlloySmelterMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return pSide == Direction.DOWN ? new int[]{2} : new int[]{0, 1};
    }
}
