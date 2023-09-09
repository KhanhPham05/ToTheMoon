package com.khanhtypo.tothemoon.api.implemented.capability.fluid;

import com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid.INbtItemFluidStorage;
import com.khanhtypo.tothemoon.api.helpers.ITagPlacer;
import com.khanhtypo.tothemoon.api.helpers.NbtHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class NbtItemFluidStorage extends NbtFluidStorage implements INbtItemFluidStorage {
    private final ItemStack container;
    private final @Nullable ITagPlacer parentTag;

    public NbtItemFluidStorage(int capacity, ItemStack container, @Nullable ITagPlacer parentTag) {
        super(capacity);
        this.container = container;
        this.parentTag = parentTag;
    }

    public NbtItemFluidStorage(int capacity, Predicate<FluidStack> validator, ItemStack container, @Nullable ITagPlacer parentTag) {
        super(capacity, validator);
        this.container = container;
        this.parentTag = parentTag;
    }

    @Override
    protected void onContentsChanged() {
        NbtHelper.saveFluidTankToItem(this, this.parentTag);
    }

    @Override
    public ItemStack getContainer() {
        return this.container;
    }
}
