package com.khanhpham.tothemoon.utils.multiblock;

import com.khanhpham.tothemoon.core.abstracts.ItemCapableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class MultiblockEntity extends ItemCapableBlockEntity {
    private @Nullable Multiblock multiblock = null;

    protected MultiblockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int inventorySize) {
        super(pType, pWorldPosition, pBlockState, inventorySize);
    }

    public void setMultiblock(@Nullable Multiblock multiblock) {
        //if already null, no need to do anything
        if (multiblock == null) {
            this.onRemove();
        }
        this.multiblock = multiblock;
    }

    @Nullable
    public Multiblock getMultiblock() {
        return multiblock;
    }

    public void onRemove() {
    }
}
