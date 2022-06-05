package com.khanhpham.tothemoon.core.blockentities.energygenerator;

import com.khanhpham.tothemoon.core.abstracts.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.energy.ExtractableEnergy;
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

    public AbstractEnergyGeneratorBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int i, int i1, int i2, Component label) {
        this(pType, pWorldPosition, pBlockState, new ExtractableEnergy(i, i1, i2), label);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AbstractEnergyGeneratorBlockEntity blockEntity) {
        blockEntity.serverTick(level, blockPos, blockState);
    }

    public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        ItemStack item = items.get(0);
        if (!energy.isFull()) {
            if (burningTime <= 0) {
                if (!item.isEmpty()) {
                    burningTime = ForgeHooks.getBurnTime(item, null);
                    burningDuration = burningTime;
                    item.shrink(1);
                    blockState = setNewBlockState(level, blockPos, blockState, AbstractEnergyGeneratorBlock.LIT, Boolean.TRUE);
                }
            } else {
                burningTime--;
                energy.receiveEnergyIgnoreCondition();
            }
        }

        if (burningTime <= 0) {
            blockState = setNewBlockState(level, blockPos, blockState, AbstractEnergyGeneratorBlock.LIT, Boolean.FALSE);
        }

        collectBlockEntities(level, blockPos);
        transferEnergy();
        setChanged(level, blockPos, blockState);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new EnergyGeneratorMenu(this, playerInventory, containerId, data);
    }
}
