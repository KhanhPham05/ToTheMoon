package com.khanhpham.tothemoon.utils.energy;

import com.google.common.collect.Lists;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public interface HasEnergyCapable {
    default <T> LazyOptional<T> getEnergyCapability(@Nullable Capability<T> cap, @Nullable Direction side, LazyOptional<T> defaultCap, LazyOptional<IEnergyStorage> energyStorageLazyOptional) {
        if (cap == CapabilityEnergy.ENERGY || getDirections().contains(side) || side == null) {
            return energyStorageLazyOptional.cast();
        }

        return defaultCap;
    }

    default List<Direction> getDirections() {
        return Lists.newArrayList(Direction.values());
    }
}
