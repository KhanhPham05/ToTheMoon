package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlackstoneFurnaceControllerBlock extends BaseBlackStoneFurnacePartBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BlackstoneFurnaceControllerBlock(MultiblockPartProperties<BlackStoneFurnaceFurnacePartTypes> properties) {
        super(properties);
        super.registerDefaultState(super.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected void buildBlockState(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    //temporary workaround with the multiblock saving issue
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            if (!pLevel.isClientSide()) {
                if (pLevel.getBlockEntity(pPos) instanceof ControllerPartEntity controllerPart) {
                    controllerPart.getMultiblockController().ifPresent(furnace -> {
                        Containers.dropContents(pLevel, pPos, furnace.itemStackHolder);
                        furnace.itemStackHolder.clearContent();
                    });
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

}
