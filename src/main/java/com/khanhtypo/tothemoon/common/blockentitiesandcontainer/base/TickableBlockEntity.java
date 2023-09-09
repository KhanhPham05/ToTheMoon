package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface TickableBlockEntity {
    static <T extends BlockEntity & TickableBlockEntity> void serverTick(Level level, BlockPos pos, BlockState blockState, T blockEntity) {
        blockEntity.serverTick(level, pos, blockState);
    }

    void serverTick(Level level, BlockPos pos, BlockState blockState);

    static <T extends BlockEntity & TickableBlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState state, T t) {
        t.clientTick(level, blockPos, state);
    }

    default void clientTick(Level level, BlockPos blockPos, BlockState state) {

    }
}
