package com.khanhtypo.tothemoon.api.implemented.capability.fluid;

import com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid.INbtFluidStorage;
import com.khanhtypo.tothemoon.api.helpers.NbtHelper;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class NbtFluidStorage extends FluidTank implements INbtFluidStorage {
    private final int initialCapacity;

    public NbtFluidStorage(int capacity) {
        super(capacity);
        initialCapacity = capacity;
    }

    public NbtFluidStorage(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
        this.initialCapacity = capacity;
    }

    @Override
    public int getInitialCapacity() {
        return this.initialCapacity;
    }

    @Override
    public CompoundTag writeToNBT(CompoundTag nbt) {
        throw new UnsupportedOperationException("Please use %s#saveFluidTankTo(CompoundTag, IFluidTank)".formatted(NbtHelper.class.getSimpleName()));
    }

    @Override
    public FluidTank readFromNBT(CompoundTag nbt) {
        throw new UnsupportedOperationException("Please use %s#loadFluidTankFrom(CompoundTag, INbtFluidStorage)".formatted(NbtHelper.class.getSimpleName()));
    }

    @Override
    public void readCompound(CompoundTag toRead) {
        if (toRead.contains("Amount", Tag.TAG_INT) && toRead.contains("FluidName", Tag.TAG_STRING)) {
            int fluidAmount = toRead.getInt("Amount");
            Fluid fluid = ModRegistries.getFromName(Registries.FLUID, new ResourceLocation(toRead.getString("FluidName")));
            @Nullable CompoundTag fluidExtraNbt = toRead.contains("FluidNbt", Tag.TAG_COMPOUND) ? toRead.getCompound("FluidNbt") : null;
            super.setFluid(new FluidStack(fluid, fluidAmount, fluidExtraNbt));
        } else {
           throw  ModUtils.fillCrashReport(new IllegalStateException(), "Missing FluidStack data ('Amount' & 'FluidName') in tag", "Missing Required Tags", crashReportCategory -> crashReportCategory.setDetail("Tag", toRead.toString()));
        }

        if (toRead.contains("TankCapacity", Tag.TAG_INT)) {
            super.setCapacity(toRead.getInt("TankCapacity"));
        }
    }
}
