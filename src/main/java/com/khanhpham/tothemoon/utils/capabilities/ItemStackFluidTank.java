package com.khanhpham.tothemoon.utils.capabilities;

import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ItemStackFluidTank implements IFluidHandlerItem {
    private final CompoundTag dataTag;
    private final ItemStack container;
    private final FluidTank fluidTank;

    public ItemStackFluidTank(@Nonnull ItemStack container, int capacity) {
        this.dataTag = LootUtils.getDataTag(container);
        this.container = container;
        this.fluidTank = LootUtils.getTankFromTag(container, capacity);
    }

    @Nonnull
    @Override
    public ItemStack getContainer() {
        return this.container;
    }

    @Override
    public int getTanks() {
        return fluidTank.getTanks();
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return this.fluidTank.getFluidInTank(tank);
    }

    private int getFluidAmount() {
        return this.fluidTank.getFluidAmount();
    }

    @Override
    public int getTankCapacity(int tank) {
        return this.fluidTank.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return this.fluidTank.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        int i = this.fluidTank.fill(resource, action);
        LootUtils.updateFluidTag(this.dataTag, this.getFluidAmount());
        return i;
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.getFluid().isSame(this.fluidTank.getFluid().getFluid())) {
            FluidStack i = this.fluidTank.drain(resource, action);
            LootUtils.updateFluidTag(this.dataTag, this.getFluidAmount());
            return i;
        }
        return FluidStack.EMPTY;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        FluidStack i = fluidTank.drain(maxDrain, action);
        LootUtils.updateFluidTag(this.dataTag, this.fluidTank.getFluid().getAmount());
        return i;
    }


}



