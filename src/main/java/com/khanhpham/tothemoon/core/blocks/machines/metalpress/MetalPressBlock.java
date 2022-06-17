package com.khanhpham.tothemoon.core.blocks.machines.metalpress;

import com.khanhpham.tothemoon.core.blockentities.others.MetalPressBlockEntity;
import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MetalPressBlock extends BaseEntityBlock<MetalPressBlockEntity> {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public MetalPressBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<MetalPressBlockEntity> supplier) {
        super(p_49224_.lightLevel((state) -> state.getValue(LIT) ? 15 : 0), supplier);

        super.registerDefaultState(super.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.FALSE));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(LIT, Boolean.FALSE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT, FACING);
    }

    @Override
    protected BlockEntityType<MetalPressBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.METAL_PRESS.get();
    }
}
