package com.khanhpham.tothemoon.utils.capabilities;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<IEnergyStorage> lazyOptional;

    public EnergyCapabilityProvider(IEnergyStorage energyStorage) {
        this.lazyOptional = LazyOptional.of(() -> energyStorage);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityEnergy.ENERGY.orEmpty(cap, this.lazyOptional);
    }
}
