package com.khanhpham.tothemoon.core.blockentities;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface FluidCapableBlockEntity {
    FluidTank getTank();

    default void setFluid(FluidStack fluid) {
        this.getTank().setFluid(fluid);
    }
}
