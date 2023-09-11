package com.khanhtypo.tothemoon.common.block;

import com.khanhtypo.tothemoon.api.abstracts.IItemDropOnBreak;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.TickableBlockEntity;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class TickableEntityBlock<T extends BlockEntity & TickableBlockEntity> extends BaseEntityBlock {
    protected final BlockEntityObject<T> blockEntityObject;

    public TickableEntityBlock(Properties pProperties, BlockEntityObject<T> blockEntityObject) {
        super(pProperties);
        this.blockEntityObject = blockEntityObject;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide) {
            if (blockEntityObject.openBlockEntityContainer(pLevel, pPos, pPlayer)) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.CONSUME;
    }

    protected final void registerDefaultState(Consumer<BlockState> defaultState) {
        BlockState state = super.stateDefinition.any();
        defaultState.accept(state);
        super.registerDefaultState(state);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return this.blockEntityObject.create(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level level, BlockState blockState, BlockEntityType<A> blockEntityType) {
        return createTickerHelper(blockEntityType, this.blockEntityObject.get(), level.isClientSide ? TickableBlockEntity::clientTick : TickableBlockEntity::serverTick);
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            if (!pLevel.isClientSide()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof Container containerBlockEntity) {
                    Containers.dropContents(pLevel, pPos, containerBlockEntity);
                } else if (blockEntity instanceof IItemDropOnBreak dropOnBreak) {
                    Containers.dropContents(pLevel, pPos, dropOnBreak.getContainer());
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        }
    }

}
