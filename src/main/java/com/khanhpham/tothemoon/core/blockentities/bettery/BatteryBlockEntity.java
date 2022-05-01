package com.khanhpham.tothemoon.core.blockentities.bettery;

import com.khanhpham.tothemoon.core.blockentities.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
    private int batteryLevel = 0;

    public BatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.BATTERY.get(), pWorldPosition, pBlockState, new Energy(50000, 1500, 1000), ModBlocks.BATTERY.get().getName(), 2);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, BatteryBlockEntity e) {
        e.serverTick(level, blockPos, blockState);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new BatteryMenu(this, playerInventory, containerId, data);
    }

    private void serverTick(Level level, BlockPos blockPos, BlockState blockState) {

        tryUpdateBlockState(level, blockPos, blockState);
        super.transferEnergyToOther(level, blockPos);
        super.receiveEnergyFromOther(level, blockPos);

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

    private void tryUpdateBlockState(Level level, BlockPos blockPos, BlockState blockState) {
        if (!level.getBlockState(blockPos.above()).is(ModBlocks.BATTERY.get())) {
            if (!level.getBlockState(blockPos.below()).is(ModBlocks.BATTERY.get()))
                setNewBlockState(level, blockPos, blockState, ModUtils.BATTERY_CONNECTION_MODE, BatteryConnectionMode.NONE);
            else
                setNewBlockState(level, blockPos, blockState, ModUtils.BATTERY_CONNECTION_MODE, BatteryConnectionMode.BELOW);
        } else if (!level.getBlockState(blockPos.below()).is(ModBlocks.BATTERY.get()) && level.getBlockState(blockPos.above()).is(ModBlocks.BATTERY.get())) {
            setNewBlockState(level, blockPos, blockState, ModUtils.BATTERY_CONNECTION_MODE, BatteryConnectionMode.ABOVE);
        }

        int energyStored = super.energy.getEnergyStored();
        int batteryLevel = (energyStored / super.energy.getMaxEnergyStored()) * 10;
        if (this.batteryLevel != batteryLevel) {
            setNewBlockState(level, blockPos, blockState, ModUtils.ENERGY_LEVEL, batteryLevel);
            this.batteryLevel = batteryLevel;
        }
    }


    @Override
    protected void saveExtra(CompoundTag tag) {
        super.saveExtra(tag);
        tag.putInt("batteryLevel", batteryLevel);
    }

    @Override
    protected void loadExtra(CompoundTag tag) {
        super.loadExtra(tag);
        this.batteryLevel = tag.getInt("batteryLevel");
    }
}
