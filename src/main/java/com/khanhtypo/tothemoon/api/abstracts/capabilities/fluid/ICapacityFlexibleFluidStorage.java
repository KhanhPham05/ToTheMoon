package com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid;

import com.khanhtypo.tothemoon.api.implemented.capability.fluid.NbtFluidStorage;
import net.minecraftforge.fluids.IFluidTank;

/**
 * An instance of {@link IFluidTank} that has its capacity altered. Useful when using with some sort of capacity upgrades.
 *
 * @see NbtFluidStorage
 */
public interface ICapacityFlexibleFluidStorage extends IFluidTank {
    int getInitialCapacity();

    default boolean isCapacityChanged() {
        return this.getInitialCapacity() != this.getCapacity();
    }
}
