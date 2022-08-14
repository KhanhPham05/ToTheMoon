package com.khanhpham.tothemoon.core.blockentities.others;

import com.khanhpham.tothemoon.core.abstracts.EnergyProcessBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressBlock;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenu;
import com.khanhpham.tothemoon.core.recipes.metalpressing.IMetalPressBlockEntity;
import com.khanhpham.tothemoon.core.recipes.metalpressing.MetalPressingRecipe;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModSoundEvents;
import com.khanhpham.tothemoon.core.energy.Energy;
import com.khanhpham.tothemoon.core.energy.EnergyOnlyReceive;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class MetalPressBlockEntity extends EnergyProcessBlockEntity implements IMetalPressBlockEntity {
    public static final int MENU_SIZE = 3;
    public static final int DATA_SIZE = 4;
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

    public MetalPressBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public MetalPressBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.METAL_PRESS.get(), blockPos, blockState, new EnergyOnlyReceive(200000), ModBlocks.METAL_PRESS.get().getName(), MENU_SIZE);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState state, MetalPressBlockEntity e) {
        e.serverTick(level, blockPos, state);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new MetalPressMenu(this, playerInventory, containerId, this.data);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        //super.receiveEnergy();

        if (!super.isEmpty(0) || !super.isEmpty(1)) {
            @Nullable MetalPressingRecipe recipe = super.getRecipe(level, MetalPressingRecipe.RECIPE_TYPE, this);

            if (recipe != null) {
                if (!super.energy.isEmpty()) {
                    if (super.isStillWorking()) {
                        this.workingTime++;
                        super.energy.consumeEnergyIgnoreCondition();
                    } else if (super.workingDuration != 0)
                        this.exchangeInputWithResults(recipe, level, pos);

                    if (super.isIdle() && super.isResultSlotFreeForProcess(super.items.get(2), recipe)) {
                        if (recipe.matches(this, level)) {
                            this.workingTime = 0;
                            this.workingDuration = recipe.getPressingTime();
                            state = super.setNewBlockState(level, pos, state, MetalPressBlock.LIT, Boolean.TRUE);
                        }
                    }
                } else {
                    state = super.setNewBlockState(level, pos, state, MetalPressBlock.LIT, Boolean.FALSE);
                }
            } else {
                super.resetTime();
                state = super.setNewBlockState(level, pos, state, MetalPressBlock.LIT, Boolean.FALSE);
            }
        }

        setChanged(level, pos, state);
    }

    private void exchangeInputWithResults(@Nonnull MetalPressingRecipe recipe, Level level, BlockPos pos) {
        super.items.get(0).shrink(1);
        if (super.items.get(2).isEmpty()) {
            super.items.set(2, recipe.assemble(this).copy());
        } else {
            this.items.get(2).grow(1);
        }
        this.resetTime();

        level.playSound(null, pos, ModSoundEvents.METAL_PRESS_USED, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    @Nonnull
    public int[] getSlotsForFace(Direction pSide) {
        return pSide == Direction.DOWN ? new int[]{2} : new int[]{0, 1};
    }
}
