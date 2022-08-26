package com.khanhpham.tothemoon.core.blocks.machines.oreprocessor;

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
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class OreProcessorBlock extends BaseEntityBlock<OreProcessorBlockEntity> {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public OreProcessorBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(3.8f, 4.3f).requiresCorrectToolForDrops());
        super.registerDefaultState(super.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockEntityType<OreProcessorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ENERGY_PROCESSOR.get();
    }

    @Override
    public OreProcessorBlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OreProcessorBlockEntity(pPos, pState);
    }
}
