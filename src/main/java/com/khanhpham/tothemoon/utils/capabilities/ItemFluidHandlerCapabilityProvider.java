package com.khanhpham.tothemoon.utils.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemFluidHandlerCapabilityProvider implements ICapabilityProvider {
    private final ItemStackFluidTank fluidHandler;
    private final LazyOptional<IFluidHandlerItem> cap;

    public ItemFluidHandlerCapabilityProvider(ItemStack container, int capacity) {
        this.fluidHandler = new ItemStackFluidTank(container, capacity);
        this.cap = LazyOptional.of(() -> this.fluidHandler);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, this.cap);
    }
}
