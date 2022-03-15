package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.utils.te.EnergyItemCapableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
        this(ModBlockEntityTypes.ALLOY_SMELTER, blockPos, state, new Energy(175000, 750, 500) {
            @Override
            public boolean canExtract() {
                return false;
            }
        }, LABEL, MENU_SIZE);
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
        AlloySmeltingRecipe recipe = this.getRecipe(level);

        if (recipe != null) {
            if (this.workingDuration != 0 && this.workingTime >= workingDuration) {
                this.exchangeInputsWithOutput(recipe);
            }

            if (!this.energy.isEmpty()) {

                if (this.isStillWorking()) {
                    this.workingTime++;
                    this.energy.consumeEnergyIgnoreCondition();
                }


                if (this.isIdle() & this.isResultSlotFreeForProcess(this.items.get(2), recipe)) {
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

    @Nullable
    private AlloySmeltingRecipe getRecipe(Level level) {
        return level.getRecipeManager().getRecipeFor(AlloySmeltingRecipe.RECIPE_TYPE, this, level).orElse(null);

    }

    private boolean isIdle() {
        return this.workingDuration == 0 && this.workingTime == 0;
    }

    private boolean isResultSlotFreeForProcess(ItemStack stack, AlloySmeltingRecipe recipe) {
        if (recipe != null) {
            if (stack.isEmpty()) {
                return true;
            } else
                return stack.is(recipe.getResultItem().getItem()) && stack.getCount() <= this.getMaxStackSize() - recipe.getResultItem().getCount();
        }

        return false;
    }

    private boolean isStillWorking() {
        return this.workingTime < this.workingDuration;
    }

    @Override
    protected void saveExtra(CompoundTag tag) {
        tag.putInt("workingDuration", this.workingDuration);
        tag.putInt("workingTime", this.workingTime);
    }

    @Override
    protected void loadExtra(CompoundTag tag) {
        this.workingTime = tag.getInt("workingTime");
        this.workingDuration = tag.getInt("workingDuration");
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
