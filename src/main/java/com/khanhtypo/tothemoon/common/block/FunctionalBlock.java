package com.khanhtypo.tothemoon.common.block;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.TickableBlockEntity;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class FunctionalBlock<T extends BlockEntity & TickableBlockEntity> extends TickableEntityBlock<T> {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public FunctionalBlock(Properties p_49795_, BlockEntityObject<T> blockEntityObject) {
        super(p_49795_.lightLevel(state -> state.getValue(LIT) ? 15 : 0).requiresCorrectToolForDrops(), blockEntityObject);
        super.registerDefaultState(
                super.getStateDefinition().any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(LIT, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT, FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}
