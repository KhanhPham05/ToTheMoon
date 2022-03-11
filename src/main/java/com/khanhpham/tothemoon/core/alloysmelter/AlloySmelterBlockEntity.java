package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.utils.te.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @see AbstractEnergyGeneratorBlockEntity
 */
public class AlloySmelterBlockEntity extends EnergyItemCapableBlockEntity {
    public static final int MENU_SIZE = 3;
    public static final TranslatableComponent LABEL = ModUtils.translate("gui.tothemoon.alloy_smelter");
    public static final int DATA_CAPACITY = 4;
    private int workingTime;
    private int workingDuration;
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> workingTime;
                case 1 -> workingDuration;
                case 2 -> energy.getEnergyStored();
                case 3 -> energy.getMaxEnergyStored();
                default -> throw new ArrayStoreException();
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
        }

        @Override
        public int getCount() {
            return DATA_CAPACITY;
        }
    };

    public AlloySmelterBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public AlloySmelterBlockEntity(BlockPos blockPos, BlockState state) {
        this(ModBlockEntityTypes.ALLOY_SMELTER, blockPos, state, new Energy(175000, 500, 1) {
            @Override
            public boolean canExtract() {
                return false;
            }
        }, LABEL, MENU_SIZE);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlloySmelterBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }

    private void serverTick(Level level, BlockPos pos, BlockState state) {
        receiveEnergyFromOther(level, pos);

        @Nullable
        AlloySmeltingRecipe recipe = level.getRecipeManager().getRecipeFor(AlloySmeltingRecipe.RECIPE_TYPE, this, level).orElse(null);

        if (recipe != null && this.workingTime >= this.workingDuration && this.workingDuration > 0) {
            this.exchangeInputsWithOutput(recipe);
        }

        if (recipe !=null && this.workingDuration > 0 && this.workingTime < this.workingDuration) {
            this.workingTime++;
        }

        if (recipe != null && !isSlotFull(items.get(2))) {
            if (recipe.matches(this, level)) {
                processRecipe(recipe);
                state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.TRUE);
            } else {
                state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.FALSE);
                resetTime();
            }
        } else {
            state = super.setNewBlockState(level, pos, state, AlloySmelterBlock.LIT, Boolean.FALSE);
            resetTime();
        }

        markDirty(level, pos, state);
    }

    private void processRecipe(@Nonnull AlloySmeltingRecipe recipe) {
        this.workingDuration = recipe.getAlloyingTime();
        this.workingTime = 0;
        this.energy.extractEnergy();
    }

    private void exchangeInputsWithOutput(@Nonnull AlloySmeltingRecipe recipe) {
        this.items.get(0).shrink(recipe.baseIngredient.amount);
        this.items.get(1).shrink(recipe.secondaryIngredient.amount);
        if (this.items.get(2).isEmpty()) {
            this.items.set(2, recipe.result);
        } else {
            this.items.get(2).grow(recipe.result.getCount());
        }

        this.resetTime();
    }

    private boolean isSlotFull(ItemStack stack) {
        return stack.getCount() == getMaxStackSize();
    }

    private void resetTime() {
        this.workingDuration = 0;
        this.workingTime = 0;
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new AlloySmelterMenu(containerId, playerInventory, this, this.data);
    }
}
