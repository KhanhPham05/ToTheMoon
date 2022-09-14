package com.khanhpham.tothemoon.core.abstracts;

import com.khanhpham.tothemoon.core.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class EnergyCapableBlockEntity extends BlockEntity {
    public final Energy energy;
    protected LazyOptional<IEnergyStorage> energyHolder;

    public Energy getEnergy() {
        return energy;
    }

    public EnergyCapableBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy) {
        super(pType, pWorldPosition, pBlockState);
        this.energy = energy;
        this.energyHolder = LazyOptional.of(() -> energy);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap.orEmpty(ForgeCapabilities.ENERGY, energyHolder.cast()).cast();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energyHolder.invalidate();
    }

    @Override
    public void reviveCaps() {
        this.energyHolder = LazyOptional.of(() -> this.energy);
    }
}
