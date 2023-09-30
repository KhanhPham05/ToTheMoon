package com.khanhtypo.tothemoon.common.capability;

import com.khanhtypo.tothemoon.api.implemented.capability.fluid.NbtFluidStorage;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleFluidStorage extends NbtFluidStorage {
    private final @Nullable BlockEntity blockEntity;
    public SimpleFluidStorage(@Nullable BlockEntity blockEntity, int capacity) {
        super(capacity);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void onContentsChanged() {
        if (this.blockEntity != null) {
            this.blockEntity.setChanged();
        }
    }
}
