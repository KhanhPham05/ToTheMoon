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
    private final LazyOptional<IFluidHandlerItem> holder;

    public ItemFluidHandlerCapabilityProvider(ItemStack container, int capacity) {
        this.fluidHandler = new ItemStackFluidTank(container, capacity);
        this.holder = LazyOptional.of(() -> this.fluidHandler);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
            return this.holder.cast();
        }

        return LazyOptional.empty();
    }
}
