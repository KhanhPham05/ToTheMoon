package com.khanhtypo.tothemoon.common.block;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.TickableBlockEntity;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TickableEntityBlock<T extends BlockEntity & TickableBlockEntity> extends BaseEntityBlock {
    protected final BlockEntityObject<T> blockEntityObject;

    public TickableEntityBlock(Properties pProperties, BlockEntityObject<T> blockEntityObject) {
        super(pProperties);
        this.blockEntityObject = blockEntityObject;
    }

    protected final void registerDefaultState(Consumer<BlockState> defaultState) {
        BlockState state = super.stateDefinition.any();
        defaultState.accept(state);
        super.registerDefaultState(state);
    }

    @Nullable
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
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, this.blockEntityObject.get(), TickableBlockEntity::tick);
    }
}
