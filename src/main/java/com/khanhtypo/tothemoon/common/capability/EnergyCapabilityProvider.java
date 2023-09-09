package com.khanhtypo.tothemoon.common.capability;

import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.utls.GuiRenderHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergyCapabilityProvider implements ICapabilityProvider {
    private final PowerStorage powerStorage;
    private final LazyOptional<IEnergyStorage> capabilityHolder;

    public EnergyCapabilityProvider(int energyCapacity) {
        this.powerStorage = new NbtPowerStorage(energyCapacity);
        this.capabilityHolder = LazyOptional.of(() -> this.powerStorage);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return this.capabilityHolder.cast();
        }

        return LazyOptional.empty();
    }

    public EnergyCapabilityProvider of(@Nullable CompoundTag itemTag) {
        if (itemTag != null && itemTag.contains("MachineData", CompoundTag.TAG_COMPOUND)) {
            CompoundTag tag = itemTag.getCompound("MachineData");
            powerStorage.deserializeNBT(tag.get("Energy"));
        }

        return this;
    }

    public boolean hasEnergy() {
        return !this.powerStorage.isEmpty();
    }

    public int getEnergy() {
        return this.powerStorage.getEnergyStored();
    }

    public Component toDisplay() {
        return GuiRenderHelper.getStorageComponent(ModLanguageGenerator.ENERGY_TOOLTIP, this.getEnergy(), this.getEnergyCapacity());
    }

    private int getEnergyCapacity() {
        return this.powerStorage.getMaxEnergyStored();
    }
}
