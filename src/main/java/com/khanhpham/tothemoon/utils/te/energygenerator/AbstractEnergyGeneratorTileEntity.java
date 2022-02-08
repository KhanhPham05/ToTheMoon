package com.khanhpham.tothemoon.utils.te.energygenerator;

import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.te.EnergyItemCapableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

/**
 * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 */
public abstract class AbstractEnergyGeneratorTileEntity extends EnergyItemCapableTileEntity {
    public static final int INVENTORY_CAPACITY = 1;
    public static final int CONTAINER_DATA_COUNT = 2;
    protected int workingTime;
    protected int workingDuration;
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> workingTime;
                case 1 -> workingDuration;
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

    public void tick(AbstractEnergyGeneratorTileEntity pBlockEntity) {
        if (pBlockEntity.isStillWorking()) {
            pBlockEntity.workingTime--;
            pBlockEntity.energy.receiveEnergy(pBlockEntity.energy.getMaxReceive(), false);
        }

        ItemStack input = pBlockEntity.items.get(0);
        if (this.canConsumeFuelAndWork(input)) {
            pBlockEntity.workingTime = pBlockEntity.getBurnTime(input);
            pBlockEntity.workingDuration = pBlockEntity.workingTime;
        }

        pBlockEntity.markDirty();
    }

    public boolean canConsumeFuelAndWork(ItemStack stack) {
        return !stack.isEmpty() && !this.isStillWorking() && this.getBurnTime(stack) > 0;
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

    public static <E extends AbstractEnergyGeneratorTileEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, E e) {
        e.tick(e);
    }
}
