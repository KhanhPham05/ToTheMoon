package com.khanhpham.tothemoon.core.blockentities.battery;

import com.khanhpham.tothemoon.core.blockentities.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.battery.BatteryBlock;
import com.khanhpham.tothemoon.core.blocks.machines.battery.BatteryMenu;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.energy.BatteryEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class BatteryBlockEntity extends EnergyItemCapableBlockEntity {
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> BatteryBlockEntity.super.energy.getEnergyStored();
                case 1 -> BatteryBlockEntity.super.energy.getMaxEnergyStored();
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


    public BatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.BATTERY.get(), pWorldPosition, pBlockState, new BatteryEnergy(75000, 2000, 2500), ModBlocks.BATTERY.get().getName(), 2);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, BatteryBlockEntity e) {
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

    private void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        blockState = tryUpdateBlockState(level, blockPos, blockState);
        super.collectBlockEntities(level, blockPos);
        this.transferEnergy();


        /*if (level.getBlockEntity(blockPos.above()) instanceof BatteryBlockEntity battery) {
            this.uploadEnergyToUpperBatteries(battery, level, blockPos.above(), this.energy.getMaxReceive());
        } else if (level.getBlockEntity(blockPos.below()) instanceof BatteryBlockEntity batteryBlockEntity) {
            if (!batteryBlockEntity.energy.isFull() && !this.energy.isEmpty() && batteryBlockEntity.getBlockState().getValue(BatteryBlock.FACING) == this.getBlockState().getValue(BatteryBlock.FACING)) {
                this.energy.extractEnergy(batteryBlockEntity.energy.receiveEnergy(this.energy.getMaxExtract(), false), false);
            }
        }*/

        if (!items.get(0).isEmpty()) {
            ItemStack stack = items.get(0);
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
            if (!super.energy.isEmpty()) {
                super.energy.extractEnergy(storage.receiveEnergy(1000, false), false);
            }
        }

        //TODO: Need some tests
        if (!items.get(1).isEmpty()) {
            ItemStack stack = items.get(1);
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
            if (!super.energy.isFull()) {
                super.energy.receiveEnergy(storage.extractEnergy(1500, false), false);
            }
        }

        setChanged(level, blockPos, blockState);
    }

    private BlockState tryUpdateBlockState(Level level, BlockPos pos, BlockState blockState) {
        int energyStored = super.energy.getEnergyStored();
        int capacity = energy.getMaxEnergyStored();
        int batteryLevel = (energyStored * 10) / capacity;
        return setNewBlockState(level, pos, blockState, BatteryBlock.ENERGY_LEVEL, batteryLevel);
        /*
        if (level.getBlockEntity(pos.above()) instanceof BatteryBlockEntity && level.getBlockEntity(pos.below()) instanceof BatteryBlockEntity) {
            state = setNewBlockState(level, pos, state, BatteryBlock.CONNECTION_MODE, BatteryConnectionMode.CROSS);
        } else if (level.getBlockEntity(pos.above()) instanceof BatteryBlockEntity) {
            state = setNewBlockState(level, pos, state, BatteryBlock.CONNECTION_MODE, BatteryConnectionMode.ABOVE);
        } else if (level.getBlockEntity(pos.below()) instanceof BatteryBlockEntity) {
            state = setNewBlockState(level, pos, state, BatteryBlock.CONNECTION_MODE, BatteryConnectionMode.BELOW);
        }*/
    }
}
