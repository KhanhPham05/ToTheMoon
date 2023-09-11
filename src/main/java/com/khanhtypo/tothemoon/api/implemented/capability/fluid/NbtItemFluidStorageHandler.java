package com.khanhtypo.tothemoon.api.implemented.capability.fluid;

import com.khanhtypo.tothemoon.api.helpers.ICompoundTagGetter;
import com.khanhtypo.tothemoon.api.helpers.ITagPlacer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NbtItemFluidStorageHandler implements ICapabilityProvider, IFluidHandlerItem, IFluidTank {
    private final LazyOptional<IFluidHandlerItem> handler;
    private final NbtItemFluidStorage itemFluidStorage;

    public NbtItemFluidStorageHandler(ItemStack itemStack, int capacity, ITagPlacer parentTag, ICompoundTagGetter compoundTagGetter) {
        this.itemFluidStorage = new NbtItemFluidStorage(capacity, itemStack, parentTag, compoundTagGetter);
        this.handler = LazyOptional.of(() -> this.itemFluidStorage);
    }

    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, this.handler);
    }

    @Override
    public ItemStack getContainer() {
        return this.itemFluidStorage.getContainer();
    }

    @Override
    public int getTanks() {
        return this.itemFluidStorage.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return this.itemFluidStorage.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return this.itemFluidStorage.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return this.itemFluidStorage.isFluidValid(tank, stack);
    }

    @Override
    public FluidStack getFluid() {
        return this.itemFluidStorage.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return this.itemFluidStorage.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return this.itemFluidStorage.getCapacity();
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return this.itemFluidStorage.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return this.itemFluidStorage.fill(resource, action);
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return this.itemFluidStorage.drain(resource, action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return this.itemFluidStorage.drain(maxDrain, action);
    }
}
