package com.khanhpham.tothemoon.core.blocks;

import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BaseEntityBlock<T extends BlockEntity> extends net.minecraft.world.level.block.BaseEntityBlock {

    public BaseEntityBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Nullable
    @Override
    public <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level pLevel, BlockState pState, BlockEntityType<A> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, getBlockEntityType(), TickableBlockEntity::staticServerTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity te = pLevel.getBlockEntity(pPos);
            if (te != null && te.getType().equals(getBlockEntityType()) && te instanceof MenuProvider) {
                pPlayer.openMenu((MenuProvider) te);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }


    protected abstract BlockEntityType<T> getBlockEntityType();

    @Override
    public final void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be != null && be.getType().equals(getBlockEntityType()) && pLevel instanceof ServerLevel && be instanceof Container te) {
                Containers.dropContents(pLevel, pPos, te);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Nullable
    @Override
    public abstract T newBlockEntity(BlockPos pPos, BlockState pState);
}
