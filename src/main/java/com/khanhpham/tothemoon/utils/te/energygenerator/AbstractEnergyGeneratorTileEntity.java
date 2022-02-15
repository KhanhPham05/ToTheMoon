package com.khanhpham.tothemoon.utils.te.energygenerator;

import com.khanhpham.tothemoon.utils.blocks.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.te.EnergyItemCapableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

/**
 * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 */
public abstract class AbstractEnergyGeneratorTileEntity extends EnergyItemCapableTileEntity {
    public static final int INVENTORY_CAPACITY = 1;
    public static final int CONTAINER_DATA_COUNT = 4;
    protected int workingTime;
    protected int workingDuration;
    public final ContainerData data = new ContainerData() {
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
            return CONTAINER_DATA_COUNT;
        }
    };

    public AbstractEnergyGeneratorTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label) {
        super(pType, pWorldPosition, pBlockState, energy, label, INVENTORY_CAPACITY);
    }

    public AbstractEnergyGeneratorTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int i, int i1, int i2, Component label) {
        this(pType, pWorldPosition, pBlockState, new Energy(i, i1, i2), label);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AbstractEnergyGeneratorTileEntity pBlockEntity) {
        /*if (pBlockEntity.isStillWorking()) {
            pBlockEntity.workingTime--;
            pBlockEntity.energy.receiveEnergy(pBlockEntity.energy.getMaxReceive(), false);
        }


        ItemStack input = pBlockEntity.items.get(0);
        if (pBlockEntity.canConsumeFuelAndWork(input)) {
            pBlockEntity.workingTime = pBlockEntity.getBurnTime(input);
            pBlockEntity.workingDuration = pBlockEntity.workingTime;
            input.shrink(1);
            level.setBlock(blockPos, blockState.setValue(AbstractEnergyGeneratorBlock.LIT, Boolean.TRUE), 2);
        } else {
            level.setBlock(blockPos, blockState.setValue(AbstractEnergyGeneratorBlock.LIT, Boolean.FALSE), 2);
        }*/

        ItemStack input = pBlockEntity.items.get(0);

        if (pBlockEntity.isStillWorking()) {
            pBlockEntity.workingTime--;
            pBlockEntity.energy.receiveEnergy(pBlockEntity.energy.getMaxReceive(), false);
        } else if (pBlockEntity.canConsumeFuelAndWork(input)) {
            input.shrink(1);
            pBlockEntity.workingTime = pBlockEntity.getBurnTime(input) / 2;
            pBlockEntity.workingDuration = pBlockEntity.workingTime;
            level.setBlock(blockPos, level.getBlockState(blockPos).setValue(AbstractEnergyGeneratorBlock.LIT, Boolean.TRUE), 2);
        } else {
            level.setBlock(blockPos, level.getBlockState(blockPos).setValue(AbstractEnergyGeneratorBlock.LIT, Boolean.FALSE), 2);
        }

        pBlockEntity.markDirty();
    }

    public boolean canConsumeFuelAndWork(ItemStack stack) {
        return !stack.isEmpty() && !this.isStillWorking() && this.getBurnTime(stack) > 0 && !super.energy.isFull();
    }

    public int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }

    public boolean isStillWorking() {
        return workingTime > 0;
    }

    private void markDirty() {
        super.setChanged();
        super.energy.setChanged();
    }
}
