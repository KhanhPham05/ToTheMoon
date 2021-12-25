package com.khanhpham.ttm.utils.block;

import com.khanhpham.ttm.testfeatures.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

/**
 * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 * @see net.minecraft.world.level.block.AbstractFurnaceBlock
 */
public class ModCapableBlock<TILE extends TickableBlockEntity> extends ModBlock implements EntityBlock {
    private final BlockEntityType.BlockEntitySupplier<TILE> blockEntitySup;

    public ModCapableBlock(Properties behaviour, BlockEntityType.BlockEntitySupplier<TILE> blockEntity) {
        super(behaviour);
        this.blockEntitySup = blockEntity;
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return blockEntitySup.create(pPos, pState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
