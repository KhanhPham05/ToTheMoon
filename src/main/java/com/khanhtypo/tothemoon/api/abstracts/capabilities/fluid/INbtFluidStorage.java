package com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid;

import com.khanhtypo.tothemoon.api.abstracts.ICompoundNbtReader;
import com.khanhtypo.tothemoon.api.helpers.IInternalFluidStorageGetter;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface INbtFluidStorage extends ICapacityFlexibleFluidStorage, ICompoundNbtReader, IInternalFluidStorageGetter {

    default int getSpace() {
        return Math.min(0, this.getCapacity() - this.getFluidAmount());
    }

    default boolean hasSpace() {
        return this.isEmpty() || this.getSpace() > 0;
    }

    default boolean isFull() {
        return this.getCapacity() <= this.getFluidAmount();
    }

    default boolean isEmpty() {
        return this.getFluid().isEmpty() || this.getSpace() == 0;
    }
}
