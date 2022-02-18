package com.khanhpham.tothemoon.utils.blocks;

import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class TileEntityBlock<T extends BlockEntity> extends BaseEntityBlock implements Mineable {
    private final MiningTool tool;
    private final BlockEntityType.BlockEntitySupplier<T> supplier;

    public TileEntityBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<T> supplier, MiningTool tool) {
        super(p_49224_);
        this.tool = tool;
        this.supplier = supplier;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return this.supplier.create(pPos, pState);
    }

    @Override
    public MiningTool getTool() {
        return this.tool;
    }
}
