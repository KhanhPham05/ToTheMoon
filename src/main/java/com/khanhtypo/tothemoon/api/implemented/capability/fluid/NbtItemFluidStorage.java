package com.khanhtypo.tothemoon.api.implemented.capability.fluid;

import com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid.INbtItemFluidStorage;
import com.khanhtypo.tothemoon.api.helpers.ICompoundTagGetter;
import com.khanhtypo.tothemoon.api.helpers.ITagPlacer;
import com.khanhtypo.tothemoon.api.helpers.NbtHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class NbtItemFluidStorage extends NbtFluidStorage implements INbtItemFluidStorage {
    private final ItemStack container;
    private final ITagPlacer tagPlacer;
    private final ICompoundTagGetter compoundTagGetter;

    public NbtItemFluidStorage(int capacity, ItemStack container, ITagPlacer tagPlacer, ICompoundTagGetter compoundTagGetter) {
        this(capacity, p -> true, container, tagPlacer, compoundTagGetter);
    }

    public NbtItemFluidStorage(int capacity, Predicate<FluidStack> validator, ItemStack container, ITagPlacer tagPlacer, ICompoundTagGetter compoundTagGetter) {
        super(capacity, validator);
        this.container = container;
        this.tagPlacer = tagPlacer;
        this.compoundTagGetter = compoundTagGetter;
    }

    @Override
    public FluidStack getFluid() {
        if (!this.container.hasTag()) {
            super.setFluid(FluidStack.EMPTY);
        } else {
            CompoundTag fluidTank = this.compoundTagGetter.getCompoundTag(this.container.getOrCreateTag());
            NbtHelper.loadFluidTankFrom(fluidTank, this);
        }
        return super.getFluid();
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        return super.drain(maxDrain, action);
    }

    @Override
    protected void onContentsChanged() {
        NbtHelper.saveFluidTankToItem(this, this.tagPlacer);
    }

    @Override
    public ItemStack getContainer() {
        return this.container;
    }
}
