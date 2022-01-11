package com.khanhpham.ttm.core.blocks;

import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blockentities.energygen.BaseGeneratorEntity;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorBlockEntity;
import com.khanhpham.ttm.utils.block.ModCapableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

/**
 * @see net.minecraft.world.level.block.AbstractFurnaceBlock
 */
public abstract class BaseEnergyGenBlock<T extends BaseGeneratorEntity> extends ModCapableBlock<T> {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BaseEnergyGenBlock(Properties behaviour, ToolType toolType, MiningLevel miningLevel, BlockEntityType.BlockEntitySupplier<T> blockEntity) {
        super(behaviour,toolType, miningLevel, blockEntity);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.FALSE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(LIT, Boolean.FALSE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level pLevel, BlockState pState, BlockEntityType<A> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, getGeneratorEntity(), BaseGeneratorEntity::tick);
    }

    protected abstract BlockEntityType<? extends EnergyGeneratorBlockEntity> getGeneratorEntity();

    @Nullable
    protected final BlockEntity getBlockEntity(Level level, BlockPos pos) {
        return level.getBlockEntity(pos);
    }
}
