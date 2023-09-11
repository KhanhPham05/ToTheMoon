package com.khanhtypo.tothemoon.common.capability;

import com.khanhtypo.tothemoon.api.implemented.capability.fluid.NbtFluidStorage;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleFluidStorage extends NbtFluidStorage {
    private final @Nullable BlockEntity blockEntity;
    private final int maxInteract;

    public SimpleFluidStorage(@Nullable BlockEntity blockEntity, int capacity, int maxInteract) {
        super(capacity);
        this.blockEntity = blockEntity;
        this.maxInteract = maxInteract;
    }

    @Override
    protected void onContentsChanged() {
        if (this.blockEntity != null) {
            this.blockEntity.setChanged();
        }
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (!resource.isEmpty()) {
            resource.setAmount(Math.min(this.maxInteract, resource.getAmount()));
        }

        return super.fill(resource, action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        return super.drain(Math.min(this.maxInteract, maxDrain), action);
    }
}
