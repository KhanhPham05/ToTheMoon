package com.khanhpham.tothemoon.core.blocks;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SpecialBlock<T extends BlockEntity> extends BaseEntityBlock<T> {
    private final Supplier<BlockEntityType<T>> blockEntityType;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public SpecialBlock(Properties p_49224_, Supplier<BlockEntityType<T>> blockEntityType) {
        super(p_49224_);
        this.blockEntityType = blockEntityType;

        BlockState state = this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false);
        super.registerDefaultState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return ModUtils.directionalPlacement(pContext, this.defaultBlockState(), FACING);
    }

    @Override
    protected BlockEntityType<T> getBlockEntityType() {
        return blockEntityType.get();
    }

    @Override
    public @Nullable T newBlockEntity(BlockPos pPos, BlockState pState) {
        return this.getBlockEntityType().create(pPos, pState);
    }
}
