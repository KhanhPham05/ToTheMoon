package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.core.abstracts.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.BaseEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.core.energy.Energy;
import com.khanhpham.tothemoon.core.energy.GeneratorEnergyStorage;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class AbstractEnergyGeneratorBlockEntity extends EnergyItemCapableBlockEntity {
    public static final int INVENTORY_CAPACITY = 1;
    public static final int CONTAINER_DATA_COUNT = 4;
    protected int burningTime;
    protected int burningDuration;
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> burningTime;
                case 1 -> burningDuration;
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

    private AbstractEnergyGeneratorBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label) {
        super(pType, pWorldPosition, pBlockState, energy, label, INVENTORY_CAPACITY);
    }

    public AbstractEnergyGeneratorBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int capacity, int generatingRate, Component label) {
        this(pType, pWorldPosition, pBlockState, new GeneratorEnergyStorage(capacity, generatingRate), label);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AbstractEnergyGeneratorBlockEntity blockEntity) {
        blockEntity.serverTick(level, blockPos, blockState);
    }

    @Override
    protected boolean canTakeItem(int index) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return ForgeHooks.getBurnTime(pStack, null) > 0;
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (this.burningTime > 0) {
            this.burningTime--;
            energy.generateEnergy();
            state = setNewBlockState(level, pos, state, BaseEnergyGeneratorBlock.LIT, true);
        } else if (!this.energy.isFull() && !items.get(0).isEmpty()) {
            int burnTime = ForgeHooks.getBurnTime(items.get(0), null);
            if (burnTime > 0) {
                items.get(0).shrink(1);
                burningDuration = burnTime;
                burningTime = burningDuration;
                state = setNewBlockState(level, pos, state, BaseEnergyGeneratorBlock.LIT, true);
            } else state = setNewBlockState(level, pos, state, BaseEnergyGeneratorBlock.LIT, false);
        } else state = setNewBlockState(level, pos, state, BaseEnergyGeneratorBlock.LIT, false);

        collectBlockEntities(level, pos);
        transferEnergy();
        setChanged(level, pos, state);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new EnergyGeneratorMenu(this, playerInventory, containerId, data);
    }
}
