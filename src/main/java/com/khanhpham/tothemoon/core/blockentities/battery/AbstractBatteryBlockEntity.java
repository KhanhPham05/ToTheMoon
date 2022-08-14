package com.khanhpham.tothemoon.core.blockentities.battery;

import com.khanhpham.tothemoon.core.abstracts.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlock;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryMenu;
import com.khanhpham.tothemoon.core.blocks.battery.creative.CreativeBatteryBlock;
import com.khanhpham.tothemoon.core.energy.BatteryEnergy;
import com.khanhpham.tothemoon.core.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class AbstractBatteryBlockEntity extends EnergyItemCapableBlockEntity {
    protected static final int CONTAINER_SIZE = 2;
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> energy.getEnergyStored();
                case 1 -> energy.getMaxEnergyStored();
                default -> throw new IllegalStateException("Unexpected value: " + pIndex);
            };
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public AbstractBatteryBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public static <T extends AbstractBatteryBlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T e) {
        e.serverTick(level, blockPos, blockState);
    }

    @Override
    public BatteryEnergy getEnergy() {
        return (BatteryEnergy) energy;
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new BatteryMenu(this, playerInventory, containerId, data);
    }

    public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        blockState = tryUpdateBlockState(level, blockPos, blockState);
        collectBlockEntities(level, blockPos);
        transferEnergy();

        if (getEnergy().getBatteryType() == BatteryEnergy.BatteryEnergyType.ALWAYS_FULL) {
            getEnergy().fillToFull();
        }

        if (!items.get(0).isEmpty()) {
            ItemStack stack = items.get(0);
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
            if (!getEnergy().isEmpty()) {
                getEnergy().extractEnergy(storage.receiveEnergy(1000, false), false);
            }
        }

        //TODO: Need some tests
        if (!items.get(1).isEmpty()) {
            ItemStack stack = items.get(1);
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
            if (!getEnergy().isFull()) {
                getEnergy().receiveEnergy(storage.extractEnergy(1500, false), false);
            }
        }

        setChanged(level, blockPos, blockState);
    }

    private BlockState tryUpdateBlockState(Level level, BlockPos pos, BlockState blockState) {
        if (blockState.getBlock() instanceof CreativeBatteryBlock)
            return setNewBlockState(level, pos, blockState, BatteryBlock.ENERGY_LEVEL, 10);

        int energyStored = getEnergy().getEnergyStored();
        int capacity = getEnergy().getMaxEnergyStored();
        int batteryLevel = (energyStored * 10) / capacity;
        return setNewBlockState(level, pos, blockState, BatteryBlock.ENERGY_LEVEL, batteryLevel);
    }


    @Override
    protected void collectBlockEntities(Level level, BlockPos pos) {
        energyStorages.clear();
        for (Direction direction : Direction.values()) {
            var be = level.getBlockEntity(pos.relative(direction));
            if (be != null && !(be instanceof BatteryBlockEntity))
                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(energy -> energyStorages.put(pos.relative(direction), energy));
        }
    }
}
