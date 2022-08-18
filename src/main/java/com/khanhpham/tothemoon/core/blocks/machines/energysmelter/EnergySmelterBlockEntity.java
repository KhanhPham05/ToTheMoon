package com.khanhpham.tothemoon.core.blocks.machines.energysmelter;

import com.khanhpham.tothemoon.core.abstracts.EnergyProcessBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelter;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterMenu;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.core.energy.Energy;
import com.khanhpham.tothemoon.core.energy.EnergyOnlyReceive;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        this(ModBlockEntities.ENERGY_SMELTER.get(), pos, state, new EnergyOnlyReceive(150000), ModBlocks.ENERGY_SMELTER.get().getName(), 2);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new EnergySmelterMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return pIndex == 0;
    }

    @Override
    protected boolean canTakeItem(int index) {
        return index == 1;
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        boolean flag = false;

        if (!items.get(0).isEmpty()) {
            SmeltingRecipe recipe = super.getRecipe(level, RecipeType.SMELTING, this);
            if (recipe != null && !energy.isEmpty() && isResultSlotFreeForProcess(items.get(1), recipe)) {
                ++workingTime;
                energy.consumeEnergyIgnoreCondition();
                flag = true;
                if (workingTime >= workingDuration) {
                    workingTime = 0;
                    workingDuration = recipe.getCookingTime() / 2;
                    burn(recipe);
                }
            } else {
                workingTime = 0;
            }
        } else if (workingTime > 0) {
            //If the energy runs out, the cooking progress will slowly decrease
            workingTime = Mth.clamp(workingTime - 2, 0, workingDuration);
        }

        state = super.setNewBlockState(level, pos, state, EnergySmelter.WORKING, flag);
        setChanged(level, pos, state);
    }

    private void burn(@Nonnull SmeltingRecipe recipe) {
        if (items.get(1).isEmpty()) {
            items.set(1, recipe.assemble(this));
        } else if (items.get(1).sameItem(recipe.getResultItem()) && recipe.getResultItem().getCount() + items.get(1).getCount() <= getMaxStackSize()) {
            items.get(1).grow(1);
        }

        items.get(0).shrink(1);
    }
}
