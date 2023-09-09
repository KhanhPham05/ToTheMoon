package com.khanhtypo.tothemoon.common.capability;

import com.khanhtypo.tothemoon.api.implemented.capability.fluid.NbtFluidStorage;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class SimpleFluidTank extends NbtFluidStorage {
    private final @Nullable BlockEntity blockEntity;

    public SimpleFluidTank(@Nullable BlockEntity blockEntity, int capacity) {
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
