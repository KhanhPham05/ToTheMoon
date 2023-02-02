package com.khanhpham.tothemoon.core.processes.single;

import com.khanhpham.tothemoon.core.abstracts.EnergyProcessBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machine.crusher.MutableContainer;
import com.khanhpham.tothemoon.core.energy.Energy;
import com.khanhpham.tothemoon.core.energy.MachineEnergy;
import com.khanhpham.tothemoon.core.recipes.SingleProcessRecipe;
import com.khanhpham.tothemoon.core.recipes.type.SingleProcessRecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class SingleProcessBlockEntity extends EnergyProcessBlockEntity {
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> workingTime;
                case 1 -> workingDuration;
                case 2 -> getEnergy().getEnergyStored();
                case 3 -> getEnergy().getMaxEnergyStored();
                default -> throw new IllegalStateException("Unexpected value: " + pIndex);
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    private final SingleProcessRecipeType recipeType;
    private final int defaultWorkingDuration;
    private final BooleanProperty litProperty;
    private final MutableContainer singleSlot = new MutableContainer(1);

    //0 - 1 process
    //2 item energy input
    public SingleProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Supplier<? extends Block> block, SingleProcessRecipeType recipe) {
        this(pType, pWorldPosition, pBlockState, block, recipe, BlockStateProperties.LIT);
    }

    //0 - 1 process
    //2 item energy input
    public SingleProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Supplier<? extends Block> block, SingleProcessRecipeType recipe, BooleanProperty litProperty) {
        this(pType, pWorldPosition, pBlockState, new MachineEnergy(500_000), block, recipe, 200, litProperty);
    }

    //0 - 1 process
    //2 item energy input
    public SingleProcessBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, Supplier<? extends Block> block, SingleProcessRecipeType recipe, int defaultWorkingDuration, BooleanProperty litProperty) {
        super(pType, pWorldPosition, pBlockState, energy, block, 3);
        this.recipeType = recipe;
        this.defaultWorkingDuration = defaultWorkingDuration;
        this.litProperty = litProperty;
    }

    @Override
    protected boolean canTakeItem(int index) {
        return index == 1;
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return pIndex == 2 ? pStack.getCapability(ForgeCapabilities.ENERGY).isPresent() : pIndex == 0;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (!this.isEmpty(2) && !this.energy.isFull()) {
            //noinspection ConstantConditions
            IEnergyStorage energyCap = this.getItem(2).getCapability(ForgeCapabilities.ENERGY).orElse(null);
            this.energy.receiveEnergy(energyCap.extractEnergy(Math.max(energyCap.getEnergyStored(), this.energy.getAvailableAmount()), false), false);
        }

        if (!this.getEnergy().isEmpty()) {
            if (!this.isEmpty(0)) {
                SingleProcessRecipe recipe = super.getRecipe(level, this.recipeType, this.singleSlot.setItems(this.items.get(0)));
                if (recipe != null && this.isResultSlotFreeForProcess(1, recipe)) {
                    if (this.isStillWorking()) {
                        this.workingTime++;
                        this.energy.consumeEnergyIgnoreCondition();
                    } else {
                        this.workingTime = 0;
                        this.workingDuration = defaultWorkingDuration;
                        state = setNewBlockState(level, pos, state, this.litProperty, true);
                    }
                } else {
                    state = resetTime(level, pos, state, this.litProperty);
                }
            }
        }

        setChanged(level, pos, state);
    }

    @NotNull
    @Override
    protected final AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new SingleProcessMenu(containerId, playerInventory, this, this.data);
    }
}
