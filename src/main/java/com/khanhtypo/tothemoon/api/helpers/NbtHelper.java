package com.khanhtypo.tothemoon.api.helpers;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid.ICapacityFlexibleFluidStorage;
import com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid.INbtFluidStorage;
import com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid.INbtItemFluidStorage;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class NbtHelper {
    private NbtHelper() {
        throw new UnsupportedOperationException("Can not construct a util class");
    }

    /**
     * @param handler the handler instance
     * @param placer  the
     */
    public static void saveFluidTankToItem(INbtItemFluidStorage handler, @Nullable ITagPlacer placer) {
        CompoundTag rootItemTag = handler.getContainer().getOrCreateTag();
        CompoundTag fluidTankTag = new CompoundTag();
        saveFluidTankTo(fluidTankTag, handler);
        if (placer != null) {
            placer.placeTag(new MutableCompoundTag(rootItemTag), "FluidTank", fluidTankTag);
        } else {
            rootItemTag.put("FluidTank", fluidTankTag);
        }
    }

    /**
     * @param rootTag   an instance of {@link CompoundTag} that the tank should be saved into.
     * @param fluidTank the tank that its data such as fluid amount, fluid name, fluid extra nbt can be saved into the tag. If the tank has its own capacity that can be changed, implement {@link ICapacityFlexibleFluidStorage}.
     */
    public static void saveFluidTankTo(CompoundTag rootTag, INbtFluidStorage fluidTank) {
        if (!isTankEmpty(fluidTank)) {
            FluidStack content = fluidTank.getInternal();
            new MutableCompoundTag(rootTag)
                    .putInt("Amount", fluidTank.getFluidAmount())
                    .putString("FluidName", ModRegistries.getNameOrThrow(Registries.FLUID, content.getFluid()))
                    .putIfPresent("FluidNbt", content.getTag())
                    .putIf("TankCapacity", fluidTank.isCapacityChanged(), IntTag.valueOf(fluidTank.getCapacity()))
                    .build();
        }
    }

    /**
     * @param parent       the {@link CompoundTag} that contains "FluidTank".
     * @param fluidStorage the fluid storage that it should be loaded into. CAN NOT BE NULL.
     */
    public static void loadFluidTankFrom(CompoundTag parent, INbtFluidStorage fluidStorage) {
        Preconditions.checkNotNull(parent, "root tag must not be null");
        Preconditions.checkNotNull(fluidStorage, "fluidStorage must not be null");
        if (parent.contains("FluidTank", CompoundTag.TAG_COMPOUND)) {
            CompoundTag fluidTankCompound = parent.getCompound("FluidTank");
            if (!fluidTankCompound.isEmpty()) {
                fluidStorage.readCompound(fluidTankCompound);
            }
            return;
        }

        throw ModUtils.fillCrashReport(new IllegalStateException(), "root tag does not have an 'FluidTank' member or it is not a compound.", "Serializing %s".formatted(fluidStorage.getClass().getSimpleName()), category -> category.setDetail("RootTag", parent.toString()));
    }

    @ApiStatus.Internal
    private static boolean isTankEmpty(INbtFluidStorage fluidTank) {
        return fluidTank.getInternal().isEmpty();
    }

}