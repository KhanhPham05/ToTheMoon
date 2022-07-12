package com.khanhpham.tothemoon.utils.capabilities;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FixedFluidTank extends FluidTank implements IFluidHandler {
    private final Fluid fixedFluid;

    public FixedFluidTank(Fluid fluid, int capacity) {
        super(capacity, fluidStack -> fluid.isSame(fluid));
        this.fixedFluid = fluid;
        super.fluid = resetFluid();
    }

    @Override
    protected void onContentsChanged() {
        if (this.fluid.getAmount() == 0 && this.fluid.getRawFluid() == Fluids.EMPTY) {
            this.fluid = resetFluid();
            ModUtils.log("Reset fixed FluidStack to [{}]", fixedFluid.getRegistryName());
        }
    }

    protected FluidStack resetFluid() {
        return new FluidStack(fixedFluid, 0);
    }
}
