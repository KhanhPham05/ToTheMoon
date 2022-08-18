package com.khanhpham.tothemoon.core.blocks.battery.creative;

import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.HashMap;

public class CreativeBatteryBlockEntity extends BlockEntity implements TickableBlockEntity {
    private final HashMap<BlockEntity, Direction> energyBlockEntities = new HashMap<>();

    public CreativeBatteryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.CREATIVE_BATTERY.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        checkBlockEntities(level, pos);
        if (!energyBlockEntities.isEmpty()) {
            for (BlockEntity blockEntity : energyBlockEntities.keySet()) {
                var energy = blockEntity.getCapability(CapabilityEnergy.ENERGY, energyBlockEntities.get(blockEntity).getOpposite()).orElse(null);
                this.receiveEnergy(energy);
            }
        }
    }

    private void receiveEnergy(IEnergyStorage energy) {
        if (energy.canReceive()) {
            int spareEnergy = energy.getMaxEnergyStored() - energy.getEnergyStored();
            energy.receiveEnergy(spareEnergy, false);
        }
    }

    private void checkBlockEntities(Level level, BlockPos pos) {
        energyBlockEntities.clear();
        for (Direction direction : Direction.values()) {
            BlockEntity te = level.getBlockEntity(pos.relative(direction));
            if (te != null && te.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).isPresent()) {
                energyBlockEntities.put(te, direction);
            }
        }
    }
}
