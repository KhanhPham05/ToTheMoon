package com.khanhpham.tothemoon.core.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface TickableBlockEntity {
    void serverTick(Level level, BlockPos pos, BlockState state);

    static <T extends BlockEntity & TickableBlockEntity> void staticServerTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }

    static <T extends BlockEntity & TickableBlockEntity> void staticClientTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        blockEntity.clientTick(level, pos, state);
    }

    default void clientTick(Level level, BlockPos pos, BlockState state) {}
}
