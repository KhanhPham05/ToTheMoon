package com.khanhpham.tothemoon.core.blockentities;

import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.energy.HasEnergyCapable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class EnergyCapableTileEntity extends BlockEntity implements HasEnergyCapable{
    protected final Energy energy;
    private final LazyOptional<IEnergyStorage> energyDataOptional;

    public EnergyCapableTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy) {
        super(pType, pWorldPosition, pBlockState);
        this.energy = energy;
        this.energyDataOptional = LazyOptional.of(() -> energy);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap.orEmpty(CapabilityEnergy.ENERGY, energyDataOptional.cast()).cast();
    }
}
