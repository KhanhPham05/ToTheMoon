package com.khanhpham.tothemoon.core.blocks.machines.energysmelter;

import com.khanhpham.tothemoon.core.blockentities.others.EnergySmelterBlockEntity;
import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergySmelter extends BaseEntityBlock<EnergySmelterBlockEntity> {
    public static final BooleanProperty WORKING = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public EnergySmelter(BlockBehaviour.Properties p_49224_) {
        super(p_49224_);
        super.registerDefaultState(defaultBlockState().setValue(WORKING, false).setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WORKING, FACING);
    }

    @Nonnull
    @Override
    protected BlockEntityType<EnergySmelterBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ENERGY_SMELTER.get();
    }

    @Override
    public @Nullable EnergySmelterBlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnergySmelterBlockEntity(pPos, pState);
    }
}
