package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface TickableBlockEntity {
    static <T extends BlockEntity & TickableBlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, T blockEntity) {
        blockEntity.serverTick(level, pos, blockState);
    }

    void serverTick(Level level, BlockPos pos, BlockState blockState);
}
