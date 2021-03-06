package com.khanhpham.tothemoon.utils.multiblock;

import com.khanhpham.tothemoon.core.abstracts.ItemCapableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class MultiblockEntity extends ItemCapableBlockEntity {
    public @Nullable Multiblock multiblock = null;

    protected MultiblockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int inventorySize) {
        super(pType, pWorldPosition, pBlockState, inventorySize);
    }

    public void setMultiblock(@Nullable Multiblock multiblock) {
        this.multiblock = multiblock;
    }

    public Multiblock getMultiblock() {
        return multiblock;
    }
}
