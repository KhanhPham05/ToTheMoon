package com.khanhpham.tothemoon.utils.capabilities;

import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class ItemStackFluidTank extends FluidTank implements IFluidHandlerItem {
    private final CompoundTag dataTag;
    private final ItemStack container;

    public ItemStackFluidTank(@Nonnull ItemStack container, int capacity) {
        super(capacity);
        this.dataTag = LootUtils.getDataTag(container);
        this.container = container;
    }

    @Override
    public int fill(@Nonnull FluidStack resource, FluidAction action) {
        int filled = super.fill(resource, action);
        LootUtils.updateFluidTag(this.dataTag, this.getFluidAmount());
        return filled;
    }

    @Nonnull
    @Override
    public ItemStack getContainer() {
        return this.container;
    }
}



